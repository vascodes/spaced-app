package com.vascodes.spaced.Presenter;

import android.content.Context;

import com.vascodes.spaced.Model.Deck;
import com.vascodes.spaced.Model.DeckDbHelper;
import com.vascodes.spaced.View.DeckViewInterface;

public class DeckPresenter {
    private DeckViewInterface view;
    private DeckDbHelper dbHelper;

    public DeckPresenter(DeckViewInterface view, Context context){
        this.view = view;
        dbHelper = new DeckDbHelper(context);
    }

    public void addDeck(String deckName){
        dbHelper.insertDeck(deckName);
        view.onDeckAddedSuccessfully(deckName);
    }

    public Deck getDeck(String deckName){
        return dbHelper.getDeck(deckName);
    }
}
