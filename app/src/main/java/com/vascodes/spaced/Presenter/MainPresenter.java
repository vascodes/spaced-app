package com.vascodes.spaced.Presenter;

import android.content.Context;

import com.vascodes.spaced.Model.DbManager;

public class MainPresenter {
    private final DbManager dbManager;

    public MainPresenter(Context context) {
        dbManager = new DbManager(context);
    }

    public void init() {
        dbManager.open();
    }

    public void close() {
        dbManager.close();
    }
}
