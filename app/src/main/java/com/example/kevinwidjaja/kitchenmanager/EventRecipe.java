package com.example.kevinwidjaja.kitchenmanager;

/** Event Recipe Model class
 * Created by kevinwidjaja on 3/30/15.
 */
public class EventRecipe {

    //private variables
    private int id;
    private int event_id;
    private int recipe_id;

    //constructor
    public EventRecipe(){
    }

    public EventRecipe(int id, int event_id, int recipe_id){
        this.id = id;
        this.event_id = event_id;
        this.recipe_id = recipe_id;
    }

    //setters
    public void setId(int id){
        this.id = id;
    }

    public void setEvent_id(int event_id){
        this.event_id = event_id;
    }

    public void setRecipe_id(int recipe_id){
        this.recipe_id = recipe_id;
    }

    //getters
    public int getId(){
        return this.id;
    }

    public int getEvent_id(){
        return this.event_id;
    }

    public int getRecipe_id(){
        return this.recipe_id;
    }

}
