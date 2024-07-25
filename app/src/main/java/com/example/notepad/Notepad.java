package com.example.notepad;

import java.util.Objects;

public class Notepad {
    public String Title;
    public String Content;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
    public Notepad(String title, String content) {
        Title = title;
        Content = content;
    }
    public Notepad() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Notepad)) return false;
        Notepad notepad = (Notepad) o;
        return Objects.equals(getTitle(), notepad.getTitle()) && Objects.equals(getContent(), notepad.getContent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getContent());
    }

    @Override
    public String toString() {
        return "Notepad{" +
                "Title='" + Title + '\'' +
                ", Content='" + Content + '\'' +
                '}';
    }
}
