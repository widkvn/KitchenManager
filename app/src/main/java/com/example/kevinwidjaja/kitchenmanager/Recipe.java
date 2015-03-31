package com.example.kevinwidjaja.kitchenmanager;

/** Recipe Model class
 * Created by kevinwidjaja on 3/30/15.
 */
public class Recipe {

    //private variables
    private int id;
    private String name;
    private String procedure;
    private int serving;
    private int picture_id;

    //constructor
    public Recipe(){
    }

    public Recipe(int id, String name, String procedure, int serving, int picture_id){
        this.id = id;
        this.name = name;
        this.procedure = procedure;
        this.serving = serving;
        this.picture_id = picture_id;
    }

    //setters
    public void setId(int id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setProcedure(String procedure){
        this.procedure = procedure;
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

    public String getName(){
        return this.name;
    }

    public String getProcedure(){
        return this.procedure;
    }

    public int getServing(){
        return this.serving;
    }

    public int getPicture_id(){
        return this.picture_id;
    }

}
