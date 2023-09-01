package com.vascodes.spaced.Model;

import androidx.annotation.Nullable;

public class Flashcard {
    private int id;
    private String deckId;
    private String question;
    private String answer;

    Flashcard(int id, String deckId, String question, String answer) {
        this.id = id;
        this.setDeckId(deckId);
        this.setQuestion(question);
        this.setAnswer(answer);
    }

    Flashcard(String deckId, String question, String answer) {
        this.setDeckId(deckId);
        this.setQuestion(question);
        this.setAnswer(answer);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDeckId() {
        return deckId;
    }

    public void setDeckId(String deckId) {
        this.deckId = deckId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}