package com.example.kevinwidjaja.kitchenmanager;

/** Recipe Inventory Model class
 * Created by kevinwidjaja on 3/30/15.
 */
public class RecipeInventory {

    //private variables
    private int id;
    private int recipe_id;
    private int inventory_id;
    private int quantity;

    //constructor
    public RecipeInventory(){
    }

    public RecipeInventory(int recipe_id, int inventory_id, int quantity){
        this.recipe_id = recipe_id;
        this.inventory_id = inventory_id;
        this.quantity = quantity;
    }

    public RecipeInventory(int id, int recipe_id, int inventory_id, int quantity){
        this.id = id;
        this.recipe_id = recipe_id;
        this.inventory_id = inventory_id;
        this.quantity = quantity;
    }

    //setters
    public void setId(int id){
        this.id = id;
    }

    public void setRecipe_id(int recipe_id){
        this.recipe_id = recipe_id;
    }

    public void setInventory_id(int inventory_id){
        this.inventory_id = inventory_id;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    //getters
    public int getId(){
        return this.id;
    }

    public int getRecipe_id(){
        return this.recipe_id;
    }

    public int getInventory_id(){
        return this.inventory_id;
    }

    public int getQuantity(){
        return this.quantity;
    }

    //Print
    public String toString(){
        return this.getId() + "," + this.getRecipe_id() + "," + this.getInventory_id() + "," + this.getQuantity();
    }

}
