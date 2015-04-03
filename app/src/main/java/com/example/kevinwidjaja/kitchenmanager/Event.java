package com.example.kevinwidjaja.kitchenmanager;

import java.util.Date;

/** Event model class
 * Created by kevinwidjaja on 3/30/15.
 */
public class Event {

    //private variables
    private int id;
    private String title;
    private Date date;
    private int serving;
    private int picture_id;

    //constructor
    public Event(){
    }

    public Event(String title, Date date, int serving, int picture_id){
        this.title = title;
        this.date = date;
        this.serving = serving;
        this.picture_id = picture_id;
    }

    public Event(int id, String title, Date date, int serving, int picture_id){
        this.id = id;
        this.title = title;
        this.date = date;
        this.serving = serving;
        this.picture_id = picture_id;
    }

    //setters
    public void setId(int id){
        this.id = id;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setDate(Date date){
        this.date = date;
    }

    public void setServing(int serving){
        this.serving = serving;
    }

    public void setPicture_id(int picture_id){
        this.picture_id = picture_id;
    }

    //getters
    public int getId(){
        return this.id;
    }

    public String getTitle(){
        return this.title;
    }

    public Date getDate(){
        return this.date;
    }

    public int getServing(){
        return this.serving;
    }

    public int getPicture_id(){
        return this.picture_id;
    }

    //Print
    public String toString(){
        return this.getId() + "," + this.getTitle() + "," + this.getDate() + "," + this.getServing() + "," + this.getPicture_id();
    }

}
