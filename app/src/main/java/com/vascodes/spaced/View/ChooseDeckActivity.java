package com.vascodes.spaced.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.vascodes.spaced.Common.Utils;
import com.vascodes.spaced.Model.Deck;
import com.vascodes.spaced.Presenter.DeckPresenter;
import com.vascodes.spaced.R;

import java.util.ArrayList;

public class ChooseDeckActivity extends AppCompatActivity implements DeckView, View.OnClickListener {
    private DeckPresenter deckPresenter;

    private Spinner spinnerDeckName;
    private Button buttonStart;
    private Button buttonCreateDeck;
    private Button buttonBack;
    private Deck selectedDeck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_deck);

        deckPresenter = new DeckPresenter(this, this);

        buttonStart = findViewById(R.id.buttonStart);
        buttonCreateDeck = findViewById(R.id.buttonCreateDeck);
        buttonBack = findViewById(R.id.buttonBack);

        buttonStart.setOnClickListener(this);
        buttonBack.setOnClickListener(this);

        // Fetch all decks.
        ArrayList<Deck> decks = deckPresenter.getAllDecks();

        // Switch to Create Deck activity if there are no decks added.
        if (!decks.isEmpty()) {
            // Populate Deck Name spinner with all decks.
            Utils.fillSpinner(this, spinnerDeckName, decks);
        } else {
            System.out.println("Empty Decks. Switching to create deck activity.");
            Intent createDeckIntent = new Intent("activity.CreateDeck");
            startActivity(createDeckIntent);
            finish();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonStart:
                if (selectedDeck == null) {
                    System.out.println("No deck selected");
                } else {
                    Intent quizIntent = new Intent("activity.Quiz");
                    quizIntent.putExtra("selectedDeckId", selectedDeck.getId());
                    quizIntent.putExtra("selectedDeckName", selectedDeck.getName());
                    startActivity(quizIntent);
                }
                break;

            case R.id.buttonCreateDeck:
                Intent createDeckIntent = new Intent("activity.CreateDeck");
                startActivity(createDeckIntent);
                break;

            case R.id.buttonBack:
                finish();
                break;
        }
    }

    @Override
    public void onDeckAddSuccess(String deckName) {

    }

    @Override
    public void onDeckAddFail(String failMessage) {

    }
}