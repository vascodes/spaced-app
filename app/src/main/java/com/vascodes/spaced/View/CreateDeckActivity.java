package com.vascodes.spaced.View;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.vascodes.spaced.Presenter.DeckPresenter;
import com.vascodes.spaced.R;

public class CreateDeckActivity extends AppCompatActivity implements DeckView {
    private DeckPresenter presenter;

    private EditText editTextDeckName;
    private Button buttonCreateDeck;
    private Button buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_deck);

        presenter = new DeckPresenter(this, this);
        editTextDeckName = findViewById(R.id.editTextDeckName);
        buttonCreateDeck = findViewById(R.id.buttonCreateDeck);
        buttonBack = findViewById(R.id.buttonBack);

        buttonCreateDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String deckName = editTextDeckName.getText().toString();
                presenter.addDeck(deckName);
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onDeckAddSuccess(String deckName) {
        String toastText = String.format("Deck created.", deckName);
        Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeckAddFail(String failMessage) {
        Toast.makeText(this, failMessage, Toast.LENGTH_LONG).show();
    }
}