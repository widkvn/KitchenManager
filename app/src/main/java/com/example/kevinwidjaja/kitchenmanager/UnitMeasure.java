package com.example.kevinwidjaja.kitchenmanager;

/** Unit measure Model class
 * Created by kevinwidjaja on 3/30/15.
 */
public class UnitMeasure {

    //private variables
    private int id;
    private String name;

    //constructor
    public UnitMeasure(){
    }

    public UnitMeasure(int id, String name){
        this.id = id;
        this.name = name;
    }

    //setters
    public void setId(int id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    //getters
    public int getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

}
