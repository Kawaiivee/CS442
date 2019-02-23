package com.example.multinotesapp;

public class Note{
    private String lastSaveDate;
    private String noteTitle;
    private String noteText;

    public Note(){
        lastSaveDate = null;
        noteTitle = "";
        noteText = "";
    }

    public Note(String l, String t, String n){
        lastSaveDate = l;
        noteTitle = t;
        noteText = n;
    }

    public String getLastSaveDate() {
        return lastSaveDate;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setLastSaveDate(String lastSaveDate) {
        this.lastSaveDate = lastSaveDate;
    }

    public void setNoteTitle(String noteTitle){
        this.noteTitle = noteTitle;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }
}
