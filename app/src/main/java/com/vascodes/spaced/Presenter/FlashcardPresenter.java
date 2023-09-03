package com.vascodes.spaced.Presenter;

import android.content.Context;
import android.database.sqlite.SQLiteException;

import com.vascodes.spaced.Model.FlashcardDbHelper;
import com.vascodes.spaced.View.FlashcardView;
import com.vascodes.spaced.Common.Utils;

public class FlashcardPresenter {
    private FlashcardView view;
    private FlashcardDbHelper dbHelper;

    public FlashcardPresenter(FlashcardView view, Context context) {
        this.view = view;
        dbHelper = new FlashcardDbHelper(context);
    }

    public FlashcardPresenter(Context context) {
        dbHelper = new FlashcardDbHelper(context);
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
            dbHelper.insertFlashcard(deckId, question, answer);
            view.onFlashcardAddedSuccessfully();
        } catch (SQLiteException e) {
            System.out.println(e.getMessage());
            view.onFlashcardAddedFailed();
        }
    }
}
