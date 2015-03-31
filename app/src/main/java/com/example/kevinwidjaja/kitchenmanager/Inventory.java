package com.example.kevinwidjaja.kitchenmanager;

/** Inventory model class
 * Created by kevinwidjaja on 3/30/15.
 */
public class Inventory {

    //private variables
    private int id;
    private int unit_id;
    private int quantity;

    //constructor
    public Inventory(){
    }

    public Inventory(int id, int unit_id, int quantity){
        this.id = id;
        this.unit_id = unit_id;
        this.quantity = quantity;
    }

    //setters
    public void setId(int id){
        this.id = id;
    }

    public void setUnit_id(int unit_id){
        this.unit_id = unit_id;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    //getters
    public int getId(){
        return this.id;
    }

    public int getUnit_id(){
        return this.unit_id;
    }

    public int getQuantity(){
        return this.quantity;
    }

}
