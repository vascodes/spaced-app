package com.vascodes.spaced.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.vascodes.spaced.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button buttonCreateDeck;
    private Button buttonAddFlashcard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonCreateDeck = findViewById(R.id.buttonCreateDeck);
        buttonAddFlashcard = findViewById(R.id.buttonAddFlashcard);

        buttonCreateDeck.setOnClickListener(this);
        buttonAddFlashcard.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
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