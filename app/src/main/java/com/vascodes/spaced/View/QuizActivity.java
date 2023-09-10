package com.vascodes.spaced.View;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.vascodes.spaced.Common.Utils;
import com.vascodes.spaced.Model.Deck;
import com.vascodes.spaced.Model.DeckDbHelper;
import com.vascodes.spaced.Presenter.QuizPresenter;
import com.vascodes.spaced.R;

public class QuizActivity extends AppCompatActivity implements QuizView, View.OnClickListener {
    private QuizPresenter quizPresenter;
    private DeckDbHelper deckDbHelper;
    private TextView textViewTitle;
    private EditText editTextQuestion;
    private EditText editTextAnswer;
    private Button buttonCheckAnswer;
    private Button buttonBack;
    private Deck deck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        this.quizPresenter = new QuizPresenter(this, this);
        this.deckDbHelper = new DeckDbHelper(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            deck = deckDbHelper.getDeck(bundle.getString("selectedDeckName"));
            System.out.println("Selected deck: " + deck);
        }

        if (deck == null) {
            System.out.println("Invalid Deck selected in Quiz Activity.");
            return;
        }

        textViewTitle = findViewById(R.id.textViewTitle);
        editTextQuestion = findViewById(R.id.editTextQuestion);
        editTextAnswer = findViewById(R.id.editTextAnswer);
        buttonCheckAnswer = findViewById(R.id.buttonCheckAnswer);
        buttonBack = findViewById(R.id.buttonBack);

        buttonCheckAnswer.setOnClickListener(this);
        buttonCheckAnswer.setEnabled(false);
        buttonBack.setOnClickListener(this);

        // Enable button to check answer only when an answer is provided.
        editTextAnswer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                buttonCheckAnswer.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                buttonCheckAnswer.setEnabled(true);
            }
        });

        quizPresenter.start(deck);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonCheckAnswer:
                String answer = editTextAnswer.getText().toString();
                quizPresenter.checkAnswer(answer);
                break;

            case R.id.buttonBack:
                finish();
                break;
        }
    }

    @Override
    public void showQuestion(String question) {
        editTextQuestion.setText(question);
    }

    @Override
    public void onAnswerCorrect() {
        Toast.makeText(this, "Correct Answer!", Toast.LENGTH_SHORT).show();
        Utils.clearEditTexts(editTextQuestion, editTextAnswer);
        quizPresenter.update();
    }

    @Override
    public void onAnswerIncorrect() {
        Toast.makeText(this, "Wrong Answer. Card placed in first box!", Toast.LENGTH_SHORT).show();
        Utils.clearEditTexts(editTextQuestion, editTextAnswer);
        quizPresenter.update();
    }

    @Override
    public void onAnswerEmpty() {
        Toast.makeText(this, "Please provide an answer.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSessionComplete() {
        Toast.makeText(this, "Session Complete!", Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void onDeckEmpty() {
        // Switch to add flashcard activity if deck does not contain any flashcard.
        Toast.makeText(this, "Please add flashcards to this deck.", Toast.LENGTH_SHORT).show();
        Intent addFlashcardIntent = new Intent("activity.AddFlashcard");
        startActivity(addFlashcardIntent);
        finish();
    }
}