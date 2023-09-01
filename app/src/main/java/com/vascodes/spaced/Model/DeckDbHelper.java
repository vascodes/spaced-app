package com.vascodes.spaced.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DeckDbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "spacedDb";
    private static final String TABLE_NAME = "Deck";

    public DeckDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableSql = "CREATE TABLE " + TABLE_NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)";
        sqLiteDatabase.execSQL(createTableSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void insertDeck(String deckName) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put("name", deckName);

            db.insert(TABLE_NAME, null, values);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Deck getDeck(int id) {
        Deck deck = null;

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";
            String[] selection = new String[]{String.valueOf(id)};

            Cursor cursor = db.rawQuery(selectQuery, selection);

            if (cursor.moveToFirst()) {
                String name = cursor.getString(1);
                deck = new Deck(id, name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return deck;
    }

    public Deck getDeck(String deckName) {
        Deck deck = null;

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE name = ?";
            String[] selection = new String[]{deckName};

            Cursor cursor = db.rawQuery(selectQuery, selection);

            if (cursor.moveToFirst()) {
                int id = Integer.parseInt(cursor.getString(0));
                deck = new Deck(id, deckName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return deck;
    }
}
