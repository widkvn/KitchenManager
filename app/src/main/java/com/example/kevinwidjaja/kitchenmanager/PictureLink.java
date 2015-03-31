package com.example.kevinwidjaja.kitchenmanager;

/** Picture link Model class
 * Created by kevinwidjaja on 3/30/15.
 */
public class PictureLink {

    //private variables
    private int id;
    private String link;

    //constructor
    public PictureLink(){
    }

    public PictureLink(int id, String link){
        this.id = id;
        this.link = link;
    }

    //setters
    public void setId(int id){
        this.id = id;
    }

    public void setLink(String link){
        this.link = link;
    }

    //getters
    public int getId(){
        return this.id;
    }

    public String getLink(){
        return this.link;
    }

}
