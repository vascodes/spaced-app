package com.vascodes.spaced.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.vascodes.spaced.Common.Constants;

import java.util.ArrayList;

public class DeckDbHelper extends SQLiteOpenHelper {
    SQLiteDatabase db;

    public DeckDbHelper(Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableSql = "CREATE TABLE " + Constants.DECK_TABLE + " (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT UNIQUE NOT NULL)";
        sqLiteDatabase.execSQL(createTableSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Constants.DECK_TABLE);
        onCreate(sqLiteDatabase);
    }

    public void insertDeck(String deckName) throws SQLiteConstraintException {
        db = this.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put("name", deckName);
            db.insertOrThrow(Constants.DECK_TABLE, null, values);
        } catch (SQLiteConstraintException sce) {
            throw sce;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    public Deck getDeck(String deckName) {
        Deck deck = null;
        db = this.getReadableDatabase();

        try {
            String selectQuery = "SELECT * FROM " + Constants.DECK_TABLE + " WHERE name = ?";
            String[] selection = new String[]{deckName};

            Cursor cursor = db.rawQuery(selectQuery, selection);

            if (cursor != null && cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                deck = new Deck(id, deckName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        return deck;
    }

    public ArrayList<Deck> getAllDecks() {
        ArrayList<Deck> decks = new ArrayList<>();
        db = this.getReadableDatabase();

        try {
            String selectQuery = "SELECT * FROM " + Constants.DECK_TABLE;
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int deckId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                    String deckName = cursor.getString(cursor.getColumnIndexOrThrow("name"));

                    Deck deck = new Deck(deckId, deckName);

                    decks.add(deck);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return decks;
    }
}
