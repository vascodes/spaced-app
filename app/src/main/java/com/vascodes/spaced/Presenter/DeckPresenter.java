package com.vascodes.spaced.Presenter;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;

import com.vascodes.spaced.Model.Deck;
import com.vascodes.spaced.Model.DeckDbHelper;
import com.vascodes.spaced.View.DeckView;

import java.util.ArrayList;

public class DeckPresenter {
    private DeckView view;
    private final DeckDbHelper dbHelper;

    public DeckPresenter(DeckView view, Context context) {
        this.view = view;
        dbHelper = new DeckDbHelper(context);
    }

    public DeckPresenter(Context context) {
        dbHelper = new DeckDbHelper(context);
    }

    public void setView(DeckView view) {
        this.view = view;
    }

    public void addDeck(Deck deck) {
        // Invalid deck name.
        if (deck.getName().equals("") || deck.getName().length() == 0)
            view.onDeckAddFail("Please enter a valid deck name.");

        try {
            deck.setName(deck.getName().trim());
            dbHelper.insertDeck(deck);
            view.onDeckAddSuccess(deck.getName());
        } catch (SQLiteConstraintException sce) {
            // Duplicate deck name.
            view.onDeckAddFail("Deck with the same name already exists.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateDeck(Deck deck) {
        dbHelper.updateDeck(deck);
    }

    public Deck getDeck(String deckName) {
        return dbHelper.getDeck(deckName);
    }

    public ArrayList<Deck> getAllDecks() {
        ArrayList<Deck> decks = dbHelper.getAllDecks();
        return decks;
    }
}
