package com.vascodes.spaced.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.vascodes.spaced.Model.Deck;
import com.vascodes.spaced.Presenter.DeckPresenter;
import com.vascodes.spaced.R;

import java.util.ArrayList;

public class ChooseDeckActivity extends AppCompatActivity implements DeckView, View.OnClickListener {
    private DeckPresenter presenter;

    private Spinner spinnerDeckName;
    private Button buttonChooseDeck;
    private Button buttonCreateDeck;
    private Button buttonBack;
    private Deck selectedDeck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_deck);

        presenter = new DeckPresenter(this, this);

        buttonChooseDeck = findViewById(R.id.buttonChooseDeck);
        buttonCreateDeck = findViewById(R.id.buttonCreateDeck);
        buttonBack = findViewById(R.id.buttonBack);

        buttonChooseDeck.setOnClickListener(this);

        buttonBack.setOnClickListener(this);
    }

    // Populate spinner with all decks.
    public void initSpinnerDeckName(ArrayList<Deck> decks) {
        ArrayAdapter<Deck> deckArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, decks);
        deckArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDeckName.setAdapter(deckArrayAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonChooseDeck:
                if (selectedDeck == null){
                    System.out.println("No deck selected");
                    return;
                }

                int deckId = selectedDeck.getId();
                System.out.println("Choose deck button clicked.");
                // TODO: Implement logic for Quiz manager.
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