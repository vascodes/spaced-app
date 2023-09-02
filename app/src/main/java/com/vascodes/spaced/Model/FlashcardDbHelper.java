package com.vascodes.spaced.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.vascodes.spaced.Common.Constants;

public class FlashcardDbHelper extends SQLiteOpenHelper {
    private final static String TABLE_NAME = "Flashcard";

    public FlashcardDbHelper(Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableQuery = String.format(
                "CREATE TABLE %s (\n" +
                        "  id INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                        "  deck_id INTEGER NOT NULL,\n" +
                        "  question TEXT NOT NULL,\n" +
                        "  answer Text NOT NULL,\n" +
                        "  FOREIGN KEY (deck_id) \n" +
                        "  \tREFERENCES Deck (id)\n" +
                        "  \tON DELETE CASCADE\n" +
                        ")", TABLE_NAME);

        sqLiteDatabase.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public long insertFlashcard(int deckId, String question, String answer) {
        long flashcardId = -1;

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put("deck_id", deckId);
            values.put("question", question);
            values.put("answer", answer);

            flashcardId = db.insert(TABLE_NAME, null, values);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return flashcardId;
    }

    public Flashcard getFlashcard(int id) {
        Flashcard flashcard = null;

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String selectQuery = String.format("SELECT * FROM %s WHERE id = ?", TABLE_NAME);
            Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(id)});

            if (cursor != null && cursor.moveToFirst()) {
                int deckId = cursor.getInt(cursor.getColumnIndexOrThrow("deck_id"));
                String question = cursor.getString(cursor.getColumnIndexOrThrow("question"));
                String answer = cursor.getString(cursor.getColumnIndexOrThrow("answer"));

                flashcard = new Flashcard(id, deckId, question, answer);
            }

            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return flashcard;
    }
}
