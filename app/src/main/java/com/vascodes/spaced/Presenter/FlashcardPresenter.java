package com.vascodes.spaced.Presenter;

import android.content.Context;
import android.database.sqlite.SQLiteException;

import com.vascodes.spaced.Model.Deck;
import com.vascodes.spaced.Model.Flashcard;
import com.vascodes.spaced.Model.FlashcardDbHelper;
import com.vascodes.spaced.View.FlashcardView;
import com.vascodes.spaced.Common.Utils;

import java.util.ArrayList;

public class FlashcardPresenter {
    private FlashcardView view;
    private FlashcardDbHelper flashcardDbHelper;

    public FlashcardPresenter(FlashcardView view, Context context) {
        this.view = view;
        flashcardDbHelper = new FlashcardDbHelper(context);
    }

    public FlashcardPresenter(Context context) {
        flashcardDbHelper = new FlashcardDbHelper(context);
    }

    public void setView(FlashcardView view) {
        this.view = view;
    }

    public void addFlashcard(int deckId, String question, String answer) {
        if (Utils.isStringEmpty(question)){
            view.onFlashcardAddedFailed("Question cannot be empty.");
            return;
        }

        if (Utils.isStringEmpty(answer)){
            view.onFlashcardAddedFailed("Answer cannot be empty.");
            return;
        }

        try {
            Flashcard flashcard = new Flashcard(deckId, question, answer, 1);
            flashcardDbHelper.insertFlashcard(flashcard);
            view.onFlashcardAddedSuccessfully();
        } catch (SQLiteException e) {
            System.out.println(e.getMessage());
            view.onFlashcardAddedFailed();
        }
    }

    // TODO: Create a shared DeckFlashcard model OR use interface communication approach for interactions between deck and flashcard models.
    public ArrayList<Flashcard> getAllFlashCards(Deck deck){
        return flashcardDbHelper.getAllFlashcardsOfADeck(deck.getId());
    }

    public void updateFlashcard(Flashcard flashcard){
        flashcardDbHelper.updateFlashcard(flashcard);
    }
}
