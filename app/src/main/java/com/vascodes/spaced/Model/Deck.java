package com.vascodes.spaced.Model;

import androidx.annotation.NonNull;

public class Deck {
    private int id;
    private String name;
    private int sessionNumber;

    public Deck(int id, String name, int sessionNumber) {
        this.id = id;
        this.name = name;
        this.sessionNumber = sessionNumber;
    }

    public Deck(String name, int sessionNumber) {
        this.name = name;
        this.sessionNumber = sessionNumber;
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

    public int getSessionNumber() {
        return sessionNumber;
    }

    public void setSessionNumber(int sessionNumber) {
        this.sessionNumber = sessionNumber;
    }


    @NonNull
    @Override
    public String toString() {
        return name.toUpperCase();
    }
}
