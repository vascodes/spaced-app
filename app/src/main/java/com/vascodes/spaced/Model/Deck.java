package com.vascodes.spaced.Model;

import androidx.annotation.NonNull;

public class Deck {
    private int id;
    private String name;

    private int sessionNumber;
    private String sessionEndDate;

    public Deck(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Deck(String name) {
        this.name = name;
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

    public String getSessionEndDate() {
        return sessionEndDate;
    }

    public void setSessionEndDate(String sessionEndDate) {
        this.sessionEndDate = sessionEndDate;
    }

    @NonNull
    @Override
    public String toString() {
        return name.toUpperCase();
    }
}
