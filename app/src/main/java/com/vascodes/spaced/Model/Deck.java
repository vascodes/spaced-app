package com.vascodes.spaced.Model;

import java.util.ArrayList;

public class Deck {
    private int id;
    private String name;
    private ArrayList<Flashcard> flashcards;

    Deck(int id, String name) {
        this.id = id;
        this.name = name;
    }

    Deck(String name) {
        this.name = name;
    }

    Deck(String name, ArrayList<Flashcard> flashcards) {
        this.name = name;
        this.flashcards = flashcards;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Flashcard> getFlashcards() {
        return flashcards;
    }

    public void setFlashcards(ArrayList<Flashcard> flashcards) {
        this.flashcards = flashcards;
    }
}
