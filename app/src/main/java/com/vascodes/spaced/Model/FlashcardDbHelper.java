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
        String createTableQuery = "CREATE TABLE " + Constants.FLASHCARD_TABLE + " (id INTEGER PRIMARY KEY AUTOINCREMENT, deck_id INTEGER NOT NULL, question TEXT NOT NULL, answer Text NOT NULL, box_number INTEGER DEFAULT 1, FOREIGN KEY (deck_id) REFERENCES Deck (id) ON DELETE CASCADE)";
        sqLiteDatabase.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Constants.FLASHCARD_TABLE);
        onCreate(sqLiteDatabase);
    }

    public void insertFlashcard(Flashcard flashcard) throws SQLiteException {
        db = this.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();

            values.put("deck_id", flashcard.getDeckId());
            values.put("question", flashcard.getQuestion());
            values.put("answer", flashcard.getAnswer());
            values.put("box_number", flashcard.getBoxNumber());

            db.insertOrThrow(Constants.FLASHCARD_TABLE, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    public Flashcard getFlashcard(int id) {
        Flashcard flashcard = null;
        db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            String selectQuery = String.format("SELECT * FROM %s WHERE id = ?", Constants.FLASHCARD_TABLE);
            cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(id)});

            if (cursor != null && cursor.moveToFirst()) {
                int deckId = cursor.getInt(cursor.getColumnIndexOrThrow("deck_id"));
                String question = cursor.getString(cursor.getColumnIndexOrThrow("question"));
                String answer = cursor.getString(cursor.getColumnIndexOrThrow("answer"));
                int boxNumber = cursor.getInt(cursor.getColumnIndexOrThrow("box_number"));

                flashcard = new Flashcard(id, deckId, question, answer, boxNumber);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }

        return flashcard;
    }

    public ArrayList<Flashcard> getAllFlashcardsOfADeck(int deckId) {
        ArrayList<Flashcard> flashcards = new ArrayList<>();
        Cursor cursor = null;
        db = this.getReadableDatabase();

        try {
            String selectQuery = "SELECT * FROM " + Constants.FLASHCARD_TABLE + " WHERE deck_id = ?";
            cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(deckId)});

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                    String question = cursor.getString(cursor.getColumnIndexOrThrow("question"));
                    String answer = cursor.getString(cursor.getColumnIndexOrThrow("answer"));
                    int boxNumber = cursor.getInt(cursor.getColumnIndexOrThrow("box_number"));

                    Flashcard flashcard = new Flashcard(id, deckId, question, answer, boxNumber);

                    flashcards.add(flashcard);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }

        return flashcards;
    }

    public void updateFlashcard(Flashcard flashcard) throws SQLiteException {
        db = this.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put("box_number", flashcard.getBoxNumber());

            int numUpdatedRows = db.update(Constants.FLASHCARD_TABLE, values, "id = ?", new String[]{String.valueOf(flashcard.getId())});
            if (numUpdatedRows == 0)
                throw new SQLiteException("Could not update box number of flashcard.");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }
}
