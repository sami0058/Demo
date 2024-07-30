package com.example.notepad;

import java.util.Objects;

public class Notes {
    public int noteId=0;
    private String title;
    private String content;

    public static final String TABLE_NAME = "Notepad";
    public static final String COLUMN_ID = "NoteId";
    public static final String COLUMN_TITLE = "Title";
    public static final String COLUMN_CONTENT = "Content";
    public static final String CREATE_TABLE = String.format("CREATE TABLE IF NOT EXISTS %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT)"
            ,TABLE_NAME,COLUMN_ID,COLUMN_TITLE,COLUMN_CONTENT);
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " +TABLE_NAME;
    public static final String SELECT_ALL_NOTES = "SELECT * FROM " + TABLE_NAME;

    public Notes (){}
    public Notes(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Notes(int noteid, String title, String content) {
        this.noteId = noteid;
        this.title = title;
        this.content = content;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Notes)) return false;
        Notes notes = (Notes) o;
        return getNoteId() == notes.getNoteId() && Objects.equals(getTitle(), notes.getTitle()) && Objects.equals(getContent(), notes.getContent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNoteId(), getTitle(), getContent());
    }

    @Override
    public String toString() {
        return "Notes{" +
                "noteId=" + noteId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
