package com.vascodes.spaced.Presenter;

import android.content.Context;

import com.vascodes.spaced.Common.Constants;
import com.vascodes.spaced.Common.Utils;
import com.vascodes.spaced.Model.Deck;
import com.vascodes.spaced.Model.Flashcard;
import com.vascodes.spaced.View.QuizView;

import java.util.ArrayList;
import java.util.HashSet;

class Box {
    int number;
    ArrayList<Flashcard> flashcards;

    Box(int number) {
        this.number = number;
        this.flashcards = new ArrayList<Flashcard>();
    }

    public boolean isEmpty() {
        return this.flashcards.isEmpty();
    }

    public void addFlashcard(Flashcard flashcard) {
        this.flashcards.add(flashcard);
    }

    public void removeFlashcard(Flashcard flashcard) {
        this.flashcards.remove(flashcard);
    }

    public Flashcard findFlashcard(int index) {
        return this.flashcards.get(index);
    }

    public Flashcard getFlashcard(int index) {
        return this.flashcards.remove(index);
    }

    public boolean containsCard(Flashcard card) {
        return this.flashcards.contains(card);
    }

    public void addFlashcards(ArrayList<Flashcard> flashcards) {
        for (Flashcard card : flashcards) {
            this.flashcards.add(card);
        }
    }

    public ArrayList<Flashcard> getFlashcards() {
        return flashcards;
    }

    public void setFlashcards(ArrayList<Flashcard> flashcards) {
        this.flashcards = flashcards;
    }

    public void clear() {
        this.flashcards.clear();
    }

    public int size() {
        return this.flashcards.size();
    }
}

public class QuizPresenter {
    private final HashSet<Flashcard> reviewedCards = new HashSet<>();
    QuizView view;
    DeckPresenter deckPresenter;
    FlashcardPresenter flashcardPresenter;
    private ArrayList<Box> boxes;
    private Deck currentDeck;
    private int currentSessionNumber;
    private Flashcard currentFlashcard;
    private Box currentBox;

    public QuizPresenter(Context context) {
        deckPresenter = new DeckPresenter(context);
        flashcardPresenter = new FlashcardPresenter(context);
    }

    public QuizPresenter(QuizView view, Context context) {
        this.view = view;
        deckPresenter = new DeckPresenter(context);
        flashcardPresenter = new FlashcardPresenter(context);
    }

    public void setView(QuizView view) {
        this.view = view;
    }

    /**
     * @param deck Object of Deck model.
     * @return number of flashcards placed in boxes.
     */
    public int placeFlashcardsInBoxes(Deck deck) {
        // Create boxes.
        if (boxes == null) {
            boxes = new ArrayList<>(Constants.LEITNER_BOXES_COUNT);
            for (int i = 0; i < Constants.LEITNER_BOXES_COUNT; i++) {
                boxes.add(new Box(i + 1));
            }
        }

        // TODO: Verify whether deck contains flashcards. Otherwise, switch to add flashcard activity.
        ArrayList<Flashcard> flashcards = flashcardPresenter.getAllFlashCards(deck);
        if (flashcards.isEmpty()) {
            view.onDeckEmpty();
            return 0;
        }

        // First session.
        if (currentSessionNumber == 1) {
            // Place all flashcards in first box.
            boxes.get(0).addFlashcards(flashcards);
        } else {
            // Resume previous session.
            // Place all flashcards in respective boxes.
            for (Flashcard f : flashcards) {
                int boxIndex = f.getBoxNumber() - 1;
                boxes.get(boxIndex).addFlashcard(f);
            }
        }

        return flashcards.size();
    }

    void stopSession() {
        System.out.println("Session Complete");

        int nextSessionNumber = (currentSessionNumber != 5) ? (currentSessionNumber + 1) : 1;
        currentDeck.setSessionNumber(nextSessionNumber);

        // TODO: Instead of using reviewedCards, try updating the flashcards using shared model / interface communication.
        System.out.println("Updating reviewed cards in db.");
        for (Flashcard f : reviewedCards) {
            System.out.println(f.getQuestion() + " box: " + f.getBoxNumber());
            flashcardPresenter.updateFlashcard(f);
        }
        deckPresenter.updateDeck(currentDeck);

        reviewedCards.clear();
        view.onSessionComplete();
    }

    void pickFlashcard(Box box) {
        int boxIndex = box.number - 1;

        if (!box.isEmpty()) {
            currentFlashcard = box.getFlashcard(box.size() - 1);
            view.showQuestion(currentFlashcard.getQuestion());
            return;
        }

        // When all flashcards of a box are reviewed.
        switch (currentSessionNumber) {
            case 1:
            case 3: {
                // Stop session after reviewing all cards in First Box.
                stopSession();
                break;
            }
            case 2:
            case 4: {
                // Review all cards in Second box.
                Box nextBox = boxes.get(boxIndex + 1);

                if (nextBox.number != 2) {
                    stopSession();
                } else {
                    // Start reviewing cards in second box.
                    this.currentBox = nextBox;
                    pickFlashcard(nextBox);
                }
                break;
            }
            case 5: {
                if (boxIndex + 1 >= boxes.size()) {
                    stopSession();
                } else {
                    // Review all cards in Second or Third box.
                    Box nextBox = boxes.get(boxIndex + 1);
                    this.currentBox = nextBox;
                    pickFlashcard(nextBox);
                }
                break;
            }
            default:
                System.out.println("Pick flashcard switch default. sessionNumber = " + currentSessionNumber);
        }
    }

    public void startQuiz(Deck deck) {
        currentDeck = deck;
        currentSessionNumber = deck.getSessionNumber();
        System.out.println("Session number: " + currentSessionNumber);

        int numFlashcardsPlaced = placeFlashcardsInBoxes(deck);

        if (numFlashcardsPlaced == 0) return;

        currentBox = boxes.get(0); // First box.
        pickFlashcard(currentBox);
    }

    public void checkAnswer(String answer) {
        // if no answer is given.
        if (Utils.isStringEmpty(answer)) {
            view.onAnswerEmpty();
            return;
        }

        int currentBoxIndex = currentBox.number - 1;
        Box firstBox = boxes.get(0);

        // Correct answer.
        if (answer.equalsIgnoreCase(currentFlashcard.getAnswer())) {
            view.onAnswerCorrect();

            // Add card to next box only if such box exists.
            if (currentBox.number + 1 <= boxes.size()) {
                // Mark card to place it in the next box.
                currentFlashcard.setBoxNumber(currentBox.number + 1);
            }
        } else {
            // Incorrect answer.
            view.onAnswerIncorrect();

            // Mark card to place it in the first box.
            currentFlashcard.setBoxNumber(1);
        }

        reviewedCards.add(currentFlashcard);
        pickFlashcard(currentBox);
    }
}
