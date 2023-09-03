package com.vascodes.spaced.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.vascodes.spaced.Common.Constants;

import java.util.ArrayList;

public class FlashcardDbHelper extends SQLiteOpenHelper {
    SQLiteDatabase db;

    public FlashcardDbHelper(Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableQuery = "CREATE TABLE " + Constants.FLASHCARD_TABLE + " (id INTEGER PRIMARY KEY AUTOINCREMENT, deck_id INTEGER NOT NULL, question TEXT NOT NULL, answer Text NOT NULL, FOREIGN KEY (deck_id) REFERENCES Deck (id) ON DELETE CASCADE)";
        sqLiteDatabase.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Constants.FLASHCARD_TABLE);
        onCreate(sqLiteDatabase);
    }

    public void insertFlashcard(int deckId, String question, String answer) throws SQLiteException {
        db = this.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();

            values.put("deck_id", deckId);
            values.put("question", question);
            values.put("answer", answer);

            db.insertOrThrow(Constants.FLASHCARD_TABLE, null, values);
        } catch (SQLiteException se) {
            throw se;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    public Flashcard getFlashcard(int id) {
        Flashcard flashcard = null;
        db = this.getReadableDatabase();

        try {
            String selectQuery = String.format("SELECT * FROM %s WHERE id = ?", Constants.FLASHCARD_TABLE);
            Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(id)});

            if (cursor != null && cursor.moveToFirst()) {
                int deckId = cursor.getInt(cursor.getColumnIndexOrThrow("deck_id"));
                String question = cursor.getString(cursor.getColumnIndexOrThrow("question"));
                String answer = cursor.getString(cursor.getColumnIndexOrThrow("answer"));

                flashcard = new Flashcard(id, deckId, question, answer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        return flashcard;
    }
}
