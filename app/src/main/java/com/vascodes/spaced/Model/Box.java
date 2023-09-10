package com.vascodes.spaced.Model;

import java.util.ArrayList;

public class Box {
    private int number;
    private ArrayList<Flashcard> flashcards;

    public Box(int number) {
        this.number = number;
        this.flashcards = new ArrayList<Flashcard>();
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public ArrayList<Flashcard> getFlashcards() {
        return flashcards;
    }

    public void setFlashcards(ArrayList<Flashcard> flashcards) {
        this.flashcards = flashcards;
    }

    public boolean isEmpty() {
        return this.flashcards.isEmpty();
    }

    public void addFlashcard(Flashcard flashcard) {
        this.flashcards.add(flashcard);
    }

    public void removeFlashcard(Flashcard flashcard) {
        this.flashcards.remove(flashcard);
    }

    public Flashcard findFlashcard(int index) {
        return this.flashcards.get(index);
    }

    public Flashcard getFlashcard(int index) {
        return this.flashcards.remove(index);
    }

    public boolean containsCard(Flashcard card) {
        return this.flashcards.contains(card);
    }

    public void addFlashcards(ArrayList<Flashcard> flashcards) {
        this.flashcards.addAll(flashcards);
    }

    public void clear() {
        this.flashcards.clear();
    }

    public int size() {
        return this.flashcards.size();
    }
}