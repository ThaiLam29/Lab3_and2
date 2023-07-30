package com.thailam.lab3_and2;

public class ToDo {
    private int ID;
    private String title;
    private String content;
    private String date;
    private String type;
    private int status;

    public ToDo() {
    }

    public ToDo(int ID, String title, String content, String date, String type, int status) {
        this.ID = ID;
        this.title = title;
        this.content = content;
        this.date = date;
        this.type = type;
        this.status = status;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
