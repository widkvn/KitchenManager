package com.example.kevinwidjaja.kitchenmanager;

/** Inventory model class
 * Created by kevinwidjaja on 3/30/15.
 */
public class Inventory {

    //private variables
    private int id;
    private String name;
    private int unit_id;
    private int quantity;
    private int quantity_req;

    //constructor
    public Inventory(){
    }

    public Inventory(String name, int unit_id, int quantity){
        this.name = name;
        this.unit_id = unit_id;
        this.quantity = quantity;
        this.quantity_req = 0;
    }

    public Inventory(int id, String name, int unit_id, int quantity){
        this.id = id;
        this.name = name;
        this.unit_id = unit_id;
        this.quantity = quantity;
        this.quantity_req = 0;
    }

    public Inventory(String name, int unit_id, int quantity, int quantity_req){
        this.name = name;
        this.unit_id = unit_id;
        this.quantity = quantity;
        this.quantity_req = quantity_req;
    }

    public Inventory(int id, String name, int unit_id, int quantity, int quantity_req){
        this.id = id;
        this.name = name;
        this.unit_id = unit_id;
        this.quantity = quantity;
        this.quantity_req = quantity_req;
    }

    //setters
    public void setId(int id){
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUnit_id(int unit_id){
        this.unit_id = unit_id;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    public void setQuantity_req(int quantity_req) {
        this.quantity_req = quantity_req;
    }

    //getters
    public int getId(){
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getUnit_id(){
        return this.unit_id;
    }

    public int getQuantity(){
        return this.quantity;
    }

    public int getQuantity_req() {
        return this.quantity_req;
    }

    //Print
    public String toString(){
        return this.getId() + "," + this.getName() + "," + this.getUnit_id() + "," + this.getQuantity();
    }

    public String toStringList(){
        return /*"ID: " + this.getId() + "\t\t*/"Quantity: " + this.getQuantity() + "\t\tMeasure: " + this.getUnit_id();
    }

}
