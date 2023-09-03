package com.vascodes.spaced.Presenter;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;

import com.vascodes.spaced.Model.Deck;
import com.vascodes.spaced.Model.DeckDbHelper;
import com.vascodes.spaced.View.DeckView;

import java.util.ArrayList;

public class DeckPresenter {
    private DeckView view;
    private DeckDbHelper dbHelper;

    public DeckPresenter(DeckView view, Context context) {
        this.view = view;
        dbHelper = new DeckDbHelper(context);
    }

    public DeckPresenter(Context context){
        dbHelper = new DeckDbHelper(context);
    }

    public void setView(DeckView view){
        this.view = view;
    }

    public void addDeck(String deckName) {
        deckName = deckName.trim();
        deckName = deckName.toLowerCase();

        // Invalid deck name.
        if (deckName.equals("") || deckName.length() == 0)
            view.onDeckAddFail("Please enter a valid deck name.");

        try {
            dbHelper.insertDeck(deckName);
            view.onDeckAddSuccess(deckName);
        } catch (SQLiteConstraintException sce) {
            // Duplicate deck name.
            view.onDeckAddFail("Deck with the same name already exists.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Deck getDeck(String deckName) {
        return dbHelper.getDeck(deckName);
    }

    public ArrayList<Deck> getAllDecks(){
        ArrayList<Deck> decks = dbHelper.getAllDecks();

//        if (decks.size() < 1){
//            return;
//        }

        return decks;
    }
}
