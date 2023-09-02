package com.vascodes.spaced.View;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.vascodes.spaced.Presenter.FlashcardPresenter;
import com.vascodes.spaced.R;

public class AddFlashcardActivity extends AppCompatActivity implements FlashcardViewInterface, View.OnClickListener {
    private FlashcardPresenter presenter;
    private Spinner spinnerDeckName;
    private EditText editTextQuestion;
    private EditText editTextAnswer;
    private Button buttonAddFlashcard;
    private Button buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_flashcard);

        presenter = new FlashcardPresenter(this, this);

        spinnerDeckName = findViewById(R.id.spinnerDeckName);
        editTextQuestion = findViewById(R.id.editTextQuestion);
        editTextAnswer = findViewById(R.id.editTextAnswer);
        buttonAddFlashcard = findViewById(R.id.buttonAddFlashcard);
        buttonBack = findViewById(R.id.buttonBack);

        buttonAddFlashcard.setOnClickListener(this);
        buttonBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonAddFlashcard:
                /**
                 * Get the selected deck and it's id from spinner.
                 * Get question, answer from respective textboxes.
                 */
                break;
            case R.id.buttonBack:
                finish();
                break;
        }
    }

    @Override
    public void onFlashcardAddedSuccessfully() {

    }

    @Override
    public void onFlashcardAddedFailed() {

    }
}