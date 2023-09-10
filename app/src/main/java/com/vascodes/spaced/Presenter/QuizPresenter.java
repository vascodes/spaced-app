package com.vascodes.spaced.Presenter;

import android.content.Context;

import com.vascodes.spaced.Model.Deck;
import com.vascodes.spaced.Model.Flashcard;
import com.vascodes.spaced.View.QuizView;

import java.util.ArrayList;

public class QuizPresenter {
    Quiz quiz;
    QuizView view;
    DeckPresenter deckPresenter;
    FlashcardPresenter flashcardPresenter;

    public QuizPresenter(QuizView view, Context context) {
        this.view = view;
        deckPresenter = new DeckPresenter(context);
        flashcardPresenter = new FlashcardPresenter(context);
    }

    public void setView(QuizView view) {
        this.view = view;
    }

    public void startQuiz(Deck deck){
        // Check if deck contains flashcards.
        ArrayList<Flashcard> flashcards = flashcardPresenter.getAllFlashCards(deck);
        if (flashcards.isEmpty()) {
            view.onDeckEmpty();
            return;
        }

        try {
            quiz = new Quiz(deck, flashcards);
            updateQuiz();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateQuiz(){
        if (quiz.isSessionComplete()){
            stopQuiz();
        } else {
            view.showQuestion(quiz.getCurrentFlashcard().getQuestion());
        }
    }

    public void checkAnswer(String answer){
        try {
            if (quiz.checkAnswer(answer)) {
                view.onAnswerCorrect();
            } else {
                view.onAnswerIncorrect();
            }
        } catch (IllegalArgumentException iae) {
            view.onAnswerEmpty();
        }

        updateQuiz();
    }

    public void stopQuiz(){
        System.out.println("Quiz stopped. Updating data.");

        for (Flashcard f : quiz.getReviewedCards()) {
            System.out.println(f.getQuestion() + " to box: " + f.getBoxNumber());
            flashcardPresenter.updateFlashcard(f);
        }
        System.out.println("Updating deck.");
        deckPresenter.updateDeck(quiz.getCurrentDeck());

        view.onSessionComplete();
    }
}
