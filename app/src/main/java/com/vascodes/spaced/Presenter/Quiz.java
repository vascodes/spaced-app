package com.vascodes.spaced.Presenter;

import com.vascodes.spaced.Common.Constants;
import com.vascodes.spaced.Common.Utils;
import com.vascodes.spaced.Model.Box;
import com.vascodes.spaced.Model.Deck;
import com.vascodes.spaced.Model.Flashcard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Quiz {
    private HashSet<Flashcard> reviewedCards = new HashSet<>();
    private ArrayList<Box> boxes;
    private Deck currentDeck;
    private int currentSessionNumber;
    private Flashcard currentFlashcard;
    private Box currentBox;
    private boolean isSessionComplete = false;

    Quiz (Deck deck, ArrayList<Flashcard> flashcards) throws Exception {
        if (flashcards.get(0).getDeckId() != deck.getId()){
            // TODO: Create custom exception for Deck mismatch.
            throw new Exception("Flashcards are not part of this deck.");
        }

        reviewedCards.clear();
        currentDeck = deck;
        currentSessionNumber = deck.getSessionNumber();
        System.out.println("Session number: " + currentSessionNumber);

        placeFlashcardsInBoxes(flashcards);

        currentBox = boxes.get(0); // First box.
        pickFlashcard(currentBox);
    }

    public Deck getCurrentDeck() {
        return currentDeck;
    }

    public Flashcard getCurrentFlashcard() {
        return currentFlashcard;
    }

    public HashSet<Flashcard> getReviewedCards() {
        return reviewedCards;
    }

    public boolean isSessionComplete() {
        return isSessionComplete;
    }

    public void placeFlashcardsInBoxes(ArrayList<Flashcard> flashcards) {
        // Create boxes.
        if (boxes == null) {
            boxes = new ArrayList<>(Constants.LEITNER_BOXES_COUNT);
            for (int i = 0; i < Constants.LEITNER_BOXES_COUNT; i++) {
                boxes.add(new Box(i + 1));
            }
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
    }

    public void pickFlashcard(Box box) {
        int boxIndex = box.getNumber() - 1;

        if (!box.isEmpty()) {
            currentFlashcard = box.getFlashcard(box.size() - 1);
            return;
        }

        // When all flashcards of a box are reviewed.
        switch (currentSessionNumber) {
            case 1:
            case 3: {
                // Stop session after reviewing all cards in First Box.
                stop();
                break;
            }
            case 2:
            case 4: {
                // Review all cards in Second box.
                Box nextBox = boxes.get(boxIndex + 1);

                if (nextBox.getNumber() != 2) {
                    stop();
                } else {
                    // Start reviewing cards in second box.
                    this.currentBox = nextBox;
                    pickFlashcard(nextBox);
                }
                break;
            }
            case 5: {
                if (boxIndex + 1 >= boxes.size()) {
                    stop();
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

    public boolean checkAnswer(String answer) throws IllegalArgumentException {
        boolean isCorrectAnswer = false;

        // If answer is empty.
        if (Utils.isStringEmpty(answer)) {
            throw new IllegalArgumentException("Answer is empty.");
        }

        int currentBoxIndex = currentBox.getNumber() - 1;
        Box firstBox = boxes.get(0);

        // Correct answer.
        if (answer.equalsIgnoreCase(currentFlashcard.getAnswer())) {
            isCorrectAnswer = true;

            // Add card to next box only if such box exists.
            if (currentBox.getNumber() + 1 <= boxes.size()) {
                // Mark card to place it in the next box.
                currentFlashcard.setBoxNumber(currentBox.getNumber() + 1);
            }

        } else {
            // Incorrect answer.
            isCorrectAnswer = false;

            // Mark card to place it in the first box.
            currentFlashcard.setBoxNumber(1);
        }

        reviewedCards.add(currentFlashcard);
        pickFlashcard(currentBox);

        return isCorrectAnswer;
    }

    public void stop() {
        System.out.println("Session Complete");
        isSessionComplete = true;

        int nextSessionNumber = (currentSessionNumber != 5) ? (currentSessionNumber + 1) : 1;
        currentDeck.setSessionNumber(nextSessionNumber);
    }
}
