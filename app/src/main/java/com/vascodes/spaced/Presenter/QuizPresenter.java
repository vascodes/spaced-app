package com.vascodes.spaced.Presenter;

import android.content.Context;

import com.vascodes.spaced.Common.Constants;
import com.vascodes.spaced.Common.Utils;
import com.vascodes.spaced.Model.Deck;
import com.vascodes.spaced.Model.Flashcard;
import com.vascodes.spaced.View.QuizView;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * TODO: Add session_count, session_date columns to deck table. By default, the session_count starts at 0.
 * TODO: Add box_number to flashcard table. By default, the box_number starts at 1.
 * <p>
 * Handling Session:
 * Pass the deck / flashcards to QuizPresenter
 * On start:
 * get the session_count of selected deck from DB.
 * use the session_count in QuizManager.start().
 * if session_count is 0
 * create 3 boxes, init box1 with all cards of deck.
 * else
 * add flashcards to boxes based on the box_number of flashcard.
 * review the boxes as required for each session.
 * <p>
 * When a deck is selected and then completed (answered all flashcards):
 * increment the session_count of that deck by 1
 * set the session_date as current date (day).
 */

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
    QuizView view;
    DeckPresenter deckPresenter;
    FlashcardPresenter flashcardPresenter;

    private ArrayList<Box> boxes;
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

    public void placeFlashcardsInBoxes(Deck deck) {
        // Create boxes.
        if (boxes == null) {
            boxes = new ArrayList<>(Constants.LEITNER_BOXES_COUNT);
            for (int i = 0; i < Constants.LEITNER_BOXES_COUNT; i++) {
                boxes.add(new Box(i + 1));
            }
        }

        // TODO: Verify whether deck contains flashcards. Otherwise, switch to add flashcard activity.
        ArrayList<Flashcard> flashcards = flashcardPresenter.getAllFlashCards(deck.getId());

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
    }

    void stopSession() {
        System.out.println("Session Complete");
        // TODO: Update box_numbers of flashcards, last_session_number of deck in DB.
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
                Box nextBox = boxes.get(boxIndex + 1);

                if (nextBox.number > boxes.size()) {
                    stopSession();
                } else {
                    // Review all cards in Second or Third box.
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
        currentSessionNumber = deck.getSessionNumber();
        System.out.println("Session Number at start: " + currentSessionNumber);

        placeFlashcardsInBoxes(deck);
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
                System.out.println("Card placed in box " + (currentBox.number + 1));
            }
        } else {
            // Incorrect answer.
            view.onAnswerIncorrect();

            // Mark card to place it in the first box.
            currentFlashcard.setBoxNumber(1);
        }

        pickFlashcard(currentBox);
    }
}
