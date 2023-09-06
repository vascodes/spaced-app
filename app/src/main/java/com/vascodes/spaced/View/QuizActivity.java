package com.vascodes.spaced.View;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.vascodes.spaced.Model.Deck;
import com.vascodes.spaced.Model.DeckDbHelper;
import com.vascodes.spaced.Presenter.QuizPresenter;
import com.vascodes.spaced.R;

import org.w3c.dom.Text;

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
        buttonCheckAnswer = findViewById(R.id.buttonCheckAnswer); // TODO: Disable buttonCheckAnswer if answer is editTextAnswer is empty.
        buttonBack = findViewById(R.id.buttonBack);

        buttonCheckAnswer.setOnClickListener(this);
        buttonBack.setOnClickListener(this);

        quizPresenter.startQuiz(deck);
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

    public void clearEditTexts(EditText... editTexts){
        for (EditText et : editTexts){
            et.setText("");
        }
    }

    @Override
    public void showQuestion(String question) {
        editTextQuestion.setText(question);
    }

    @Override
    public void onAnswerCorrect() {
        Toast.makeText(this, "Correct Answer!", Toast.LENGTH_SHORT).show();
        clearEditTexts(editTextQuestion, editTextAnswer);
    }

    @Override
    public void onAnswerIncorrect() {
        Toast.makeText(this, "Wrong Answer. Card placed in first box!", Toast.LENGTH_SHORT).show();
        clearEditTexts(editTextQuestion, editTextAnswer);
    }

    @Override
    public void onAnswerEmpty() {
        Toast.makeText(this, "Please enter an answer.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSessionComplete() {
        Toast.makeText(this, "Session Complete!", Toast.LENGTH_LONG).show();
        clearEditTexts(editTextQuestion, editTextAnswer);
    }
}