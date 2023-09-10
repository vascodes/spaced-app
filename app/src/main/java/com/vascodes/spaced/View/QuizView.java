package com.vascodes.spaced.View;

public interface QuizView {
    void showQuestion(String question);

    void onAnswerCorrect();

    void onAnswerIncorrect();

    void onAnswerEmpty();

    void onSessionComplete();

    void onDeckEmpty();
}
