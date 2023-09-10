package com.vascodes.spaced.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.vascodes.spaced.Common.Constants;

public class DbManager extends SQLiteOpenHelper {
    SQLiteDatabase db;
    DeckDbHelper deckDbHelper;
    FlashcardDbHelper flashcardDbHelper;

    public DbManager(Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
        deckDbHelper = new DeckDbHelper(context);
        flashcardDbHelper = new FlashcardDbHelper(context);
    }

    public void open() {
        db = this.getReadableDatabase();
    }

    public void close() {
        if (db != null) db.close();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        deckDbHelper.onCreate(sqLiteDatabase);
        flashcardDbHelper.onCreate(sqLiteDatabase);
        System.out.println("Created all tables.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        deckDbHelper.onUpgrade(sqLiteDatabase, i, i1);
        flashcardDbHelper.onUpgrade(sqLiteDatabase, i, i1);
        System.out.println("Updated all tables.");
    }
}
