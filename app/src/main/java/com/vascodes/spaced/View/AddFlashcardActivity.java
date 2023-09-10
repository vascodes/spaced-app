package com.vascodes.spaced.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.vascodes.spaced.Common.Utils;
import com.vascodes.spaced.Model.Deck;
import com.vascodes.spaced.Presenter.DeckPresenter;
import com.vascodes.spaced.Presenter.FlashcardPresenter;
import com.vascodes.spaced.R;

import java.util.ArrayList;

public class AddFlashcardActivity extends AppCompatActivity implements FlashcardView, DeckView, View.OnClickListener, AdapterView.OnItemSelectedListener {
    private FlashcardPresenter flashcardPresenter;
    private DeckPresenter deckPresenter;
    private Spinner spinnerDeckName;
    private EditText editTextQuestion;
    private EditText editTextAnswer;
    private Button buttonAddFlashcard;
    private Button buttonBack;
    private Deck selectedDeck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_flashcard);

        flashcardPresenter = new FlashcardPresenter(this, this);
        deckPresenter = new DeckPresenter(this, this);

        spinnerDeckName = findViewById(R.id.spinnerDeckName);
        editTextQuestion = findViewById(R.id.editTextQuestion);
        editTextAnswer = findViewById(R.id.editTextAnswer);
        buttonAddFlashcard = findViewById(R.id.buttonAddFlashcard);
        buttonBack = findViewById(R.id.buttonBack);

        buttonAddFlashcard.setOnClickListener(this);
        buttonBack.setOnClickListener(this);
        spinnerDeckName.setOnItemSelectedListener(this);

        // Fetch all decks.
        ArrayList<Deck> decks = deckPresenter.getAllDecks();

        // Switch to Create Deck activity if there are no decks added.
        if (!decks.isEmpty()){
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
            case R.id.buttonAddFlashcard:
                if (selectedDeck == null){
                    System.out.println("No deck selected");
                    return;
                }

                int deckId = selectedDeck.getId();
                String question = editTextQuestion.getText().toString();
                String answer = editTextAnswer.getText().toString();
                flashcardPresenter.addFlashcard(deckId, question, answer);
                Utils.clearEditTexts(editTextQuestion, editTextAnswer);
                break;

            case R.id.buttonBack:
                finish();
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        selectedDeck = (Deck) adapterView.getItemAtPosition(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onFlashcardAddedSuccessfully() {
        Toast.makeText(this, "Flashcard added!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFlashcardAddedFailed() {
        Toast.makeText(this, "Could not add flashcard.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFlashcardAddedFailed(String failMessage) {
        Toast.makeText(this, "Could not add flashcard." + failMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDeckAddSuccess(String deckName) {

    }

    @Override
    public void onDeckAddFail(String failMessage) {

    }
}