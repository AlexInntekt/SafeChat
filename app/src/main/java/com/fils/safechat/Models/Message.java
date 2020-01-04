package com.fils.safechat.Models;

import java.sql.Date;
import java.sql.Timestamp;

public class Message {
    private String author;
    private String text;
    private String createdAt;

    public Message(String author, String text, String createdAt){
        this.author = author;
        this.text = text;
        this.createdAt = createdAt;
    }

    public String getAuthor(){
        return this.author;
    }

    public String getText(){

        return this.text;
    }

    public void setAuthor(String author){
        this.author = author;
    }

    public void setText(String text){
        this.text = text;
    }

    public String getCreatedAt(){

        return this.createdAt;
    }

    public void setCreatedAt(String createdAt){
        this.createdAt = createdAt;
    }

    @Override
    public String toString(){
        Date date;
        try {
            date = new java.sql.Date(Long.parseLong(createdAt));
        } catch(Exception e){
            return this.text + "\n" + this.author;
        }
        return this.text + "\n" + this.author + " " + date;
    }
}
