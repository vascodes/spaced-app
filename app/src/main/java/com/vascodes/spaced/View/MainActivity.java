package com.vascodes.spaced.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.vascodes.spaced.Presenter.MainPresenter;
import com.vascodes.spaced.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button buttonStart;
    private Button buttonCreateDeck;
    private Button buttonAddFlashcard;

    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenter(this);
        presenter.init();

        buttonStart = findViewById(R.id.buttonStart);
        buttonCreateDeck = findViewById(R.id.buttonCreateDeck);
        buttonAddFlashcard = findViewById(R.id.buttonAddFlashcard);

        buttonStart.setOnClickListener(this);
        buttonCreateDeck.setOnClickListener(this);
        buttonAddFlashcard.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonStart:
                Intent chooseDeckIntent = new Intent("activity.ChooseDeck");
                startActivity(chooseDeckIntent);
                break;

            case R.id.buttonCreateDeck:
                Intent createDeckIntent = new Intent("activity.CreateDeck");
                startActivity(createDeckIntent);
                break;

            case R.id.buttonAddFlashcard:
                Intent addFlashcardIntent = new Intent("activity.AddFlashcard");
                startActivity(addFlashcardIntent);
                break;
        }
    }
}