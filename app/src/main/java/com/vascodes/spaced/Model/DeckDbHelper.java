package com.vascodes.spaced.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
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
        String createTableSql = "CREATE TABLE " + Constants.DECK_TABLE + " (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT UNIQUE NOT NULL, session_number INTEGER DEFAULT 1)";
        sqLiteDatabase.execSQL(createTableSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Constants.DECK_TABLE);
        onCreate(sqLiteDatabase);
    }

    public void insertDeck(Deck deck) throws SQLiteConstraintException {
        db = this.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put("name", deck.getName());
            values.put("session_number", deck.getSessionNumber());
            db.insertOrThrow(Constants.DECK_TABLE, null, values);
        } catch (SQLiteConstraintException sce) {
            throw sce;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    public void updateDeck(Deck deck) throws SQLiteException {
        db = this.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put("name", deck.getName());
            values.put("session_number", deck.getSessionNumber());

            int numUpdatedRows = db.update(Constants.DECK_TABLE, values, "id = ?", new String[]{String.valueOf(deck.getId())});
            if (numUpdatedRows == 0)
                throw new SQLiteException("Error updating Deck table.");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    public Deck getDeck(String deckName) {
        Deck deck = null;
        db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            String selectQuery = "SELECT * FROM " + Constants.DECK_TABLE + " WHERE name = ?";
            String[] selection = new String[]{deckName};

            cursor = db.rawQuery(selectQuery, selection);

            if (cursor != null && cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                int sessionNumber = cursor.getInt(cursor.getColumnIndexOrThrow("session_number"));

                deck = new Deck(id, deckName, sessionNumber);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }

        return deck;
    }

    public ArrayList<Deck> getAllDecks() {
        ArrayList<Deck> decks = new ArrayList<>();
        db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            String selectQuery = "SELECT * FROM " + Constants.DECK_TABLE;
            cursor = db.rawQuery(selectQuery, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int deckId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                    String deckName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                    int sessionNumber = cursor.getInt(cursor.getColumnIndexOrThrow("session_number"));

                    Deck deck = new Deck(deckId, deckName, sessionNumber);

                    decks.add(deck);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            if (cursor != null) cursor.close();
            e.printStackTrace();
        }

        return decks;
    }
}
