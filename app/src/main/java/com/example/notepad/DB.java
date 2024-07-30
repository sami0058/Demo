package com.example.notepad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DB extends SQLiteOpenHelper {
    public static DB instance;
    private static final String DATABASE_NAME = "NOTEPAD";
    private static final int DATABASE_VERSION = 6;

    private DB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DB getInstance(Context context) {
        if (instance == null) {
            instance = new DB(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Notes.CREATE_TABLE);
        db.execSQL(Notes.DROP_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL(Notes.DROP_TABLE);
            db.execSQL(Notes.CREATE_TABLE);
        }
    }

    public boolean insertNote(Notes notes) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Notes.COLUMN_TITLE, notes.getTitle());
        contentValues.put(Notes.COLUMN_CONTENT, notes.getContent());
        long rowId;
        try {
            rowId = db.insert(Notes.TABLE_NAME, null, contentValues);
        } catch (Exception ex) {
            return false;
        }
        return rowId != -1;
    }

    public boolean updateNote(Notes notes) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Notes.COLUMN_TITLE, notes.getTitle());
        contentValues.put(Notes.COLUMN_CONTENT, notes.getContent());
        long rowId;
        try {
            rowId = db.update(Notes.TABLE_NAME, contentValues, Notes.COLUMN_ID + "=?", new String[]{String.valueOf(notes.getNoteId())});
        } catch (Exception ex) {
            return false;
        }
        return rowId != -1;
    }

    public boolean deleteNote(Notes notes) {
        SQLiteDatabase db = getWritableDatabase();
        long rowId;
        try {
            rowId = db.delete(Notes.TABLE_NAME, Notes.COLUMN_ID + "=?", new String[]{String.valueOf(notes.getNoteId())});
        } catch (Exception ex) {
            return false;

        }
        return rowId != -1;
    }

    public List<Notes> fetchNotes() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(Notes.SELECT_ALL_NOTES, null);
        List<Notes> notes = new ArrayList<>(cursor.getCount());
        if (cursor.moveToFirst()) {
            do {
                Notes note = new Notes();
                int index = cursor.getColumnIndex(Notes.COLUMN_ID);
                note.setNoteId(cursor.getInt(index));
                index = cursor.getColumnIndex(Notes.COLUMN_TITLE);
                note.setTitle(cursor.getString(index));
                index = cursor.getColumnIndex(Notes.COLUMN_CONTENT);
                note.setContent(cursor.getString(index));
                notes.add(note);
            } while (
                    cursor.moveToNext());
        }
            cursor.close();
            return notes;




    }

}