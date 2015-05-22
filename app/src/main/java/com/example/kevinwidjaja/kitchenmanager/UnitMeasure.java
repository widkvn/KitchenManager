package com.example.kevinwidjaja.kitchenmanager;

/** Unit measure Model class
 * Created by kevinwidjaja on 3/30/15.
 */
public class UnitMeasure
{

    //private variables
    private int id;
    private String metric;

    //constructor
    public UnitMeasure(){
    }

    public UnitMeasure(String metric){
        this.metric = metric;
    }

    public UnitMeasure(int id, String metric){
        this.id = id;
        this.metric = metric;
    }

    //setters
    public void setId(int id){
        this.id = id;
    }

    public void setMetric(String metric){
        this.metric = metric;
    }

    //getters
    public int getId(){
        return this.id;
    }

    public String getMetric(){
        return this.metric;
    }

    //Print
    public String toString(){
        return this.getId() + "," + this.getMetric();
    }

}
