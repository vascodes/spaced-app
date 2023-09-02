package com.vascodes.spaced.Presenter;

import android.content.Context;

import com.vascodes.spaced.Model.FlashcardDbHelper;
import com.vascodes.spaced.View.FlashcardViewInterface;

public class FlashcardPresenter {
    private FlashcardViewInterface view;
    private FlashcardDbHelper dbHelper;

    public FlashcardPresenter(FlashcardViewInterface view, Context context){
        this.view = view;
        this.dbHelper = new FlashcardDbHelper(context);
    }

    public void addFlashcard(int deckId, String question, String answer){
        long flashCardId = dbHelper.insertFlashcard(deckId, question, answer);

        if (flashCardId == -1){
            view.onFlashcardAddedFailed();
            return;
        }

        view.onFlashcardAddedSuccessfully();
    }
}
