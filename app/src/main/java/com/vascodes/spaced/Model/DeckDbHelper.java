package com.vascodes.spaced.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
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
        String createTableSql = "CREATE TABLE " + TABLE_NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT UNIQUE NOT NULL)";
        sqLiteDatabase.execSQL(createTableSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void insertDeck(String deckName) throws SQLiteConstraintException {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put("name", deckName);
            db.insertOrThrow(TABLE_NAME, null, values);
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
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE name = ?";
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
}
