package com.vascodes.spaced.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.vascodes.spaced.Common.Constants;

public class DbManager extends SQLiteOpenHelper {
    SQLiteDatabase db;

    public DbManager(Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
    }

    public void open() {
        db = this.getReadableDatabase();
    }

    public void close() {
        if (db != null) db.close();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE Deck (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT UNIQUE NOT NULL)");
        sqLiteDatabase.execSQL("CREATE TABLE Flashcard (id INTEGER PRIMARY KEY AUTOINCREMENT, deck_id INTEGER NOT NULL, question TEXT NOT NULL, answer Text NOT NULL, FOREIGN KEY (deck_id) REFERENCES Deck (id) ON DELETE CASCADE)");
        System.out.println("Created all tables.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Deck");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Flashcard");
        onCreate(sqLiteDatabase);
    }
}
