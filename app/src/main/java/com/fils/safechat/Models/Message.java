package com.fils.safechat.Models;

import com.fils.safechat.Services.Encrypting;

import java.sql.Date;

public class Message implements Comparable<Message> {
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
        Encrypting encrypting = new Encrypting();

        Date date;
        String decryptedText = new String();
        try {
            date = new java.sql.Date(Long.parseLong(createdAt));
            decryptedText = encrypting.decrypt(this.text);
        } catch(Exception e){
            return decryptedText + "\n" + this.author;
        }
        return decryptedText + "\n" + this.author + " " + date;
    }

    @Override
    public int compareTo(Message o) {
        return this.createdAt.compareTo(o.createdAt);
    }
}
