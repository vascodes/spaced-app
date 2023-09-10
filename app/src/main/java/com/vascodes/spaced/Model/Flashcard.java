package com.vascodes.spaced.Model;

import androidx.annotation.Nullable;

import java.util.Objects;

public class Flashcard {
    private int id;
    private int deckId;
    private String question;
    private String answer;
    private int boxNumber;

    public Flashcard(int id, int deckId, String question, String answer, int boxNumber) {
        this.id = id;
        this.setDeckId(deckId);
        this.setQuestion(question);
        this.setAnswer(answer);
        this.boxNumber = boxNumber;
    }

    public Flashcard(int deckId, String question, String answer, int boxNumber) {
        this.setDeckId(deckId);
        this.setQuestion(question);
        this.setAnswer(answer);
        this.boxNumber = boxNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDeckId() {
        return deckId;
    }

    public void setDeckId(int deckId) {
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

    public int getBoxNumber() {
        return boxNumber;
    }

    public void setBoxNumber(int boxNumber) {
        this.boxNumber = boxNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flashcard flashcard = (Flashcard) o;
        return getId() == flashcard.getId() && getDeckId() == flashcard.getDeckId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDeckId());
    }
}
