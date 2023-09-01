package com.vascodes.spaced.View;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.vascodes.spaced.Presenter.DeckPresenter;
import com.vascodes.spaced.R;

public class MainActivity extends AppCompatActivity implements DeckViewInterface {
    private DeckPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new DeckPresenter(this, this);
    }

    public void handleClick(View view) {
        String testDeckName = "test";
        presenter.addDeck(testDeckName);
    }

    @Override
    public void onDeckAddedSuccessfully(String deckName) {
        String successText = String.format("Deck %s created.", deckName);
        Toast.makeText(this, successText, Toast.LENGTH_SHORT).show();
    }
}