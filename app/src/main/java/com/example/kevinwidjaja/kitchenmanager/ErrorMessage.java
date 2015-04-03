package com.example.kevinwidjaja.kitchenmanager;

/** Error Message Model class
 * Created by kevinwidjaja on 3/30/15.
 */
public class ErrorMessage {

    //private variables
    private int id;
    private String msg;

    //constructor
    public ErrorMessage(){
    }

    public ErrorMessage(String msg){
        this.msg = msg;
    }

    public ErrorMessage(int id, String msg){
        this.id = id;
        this.msg = msg;
    }

    //setters
    public void setId(int id){
        this.id = id;
    }

    public void setMsg(String msg){
        this.msg = msg;
    }

    //getters
    public int getId(){
        return this.id;
    }

    public String getMsg(){
        return this.msg;
    }

    //Print
    public String toString(){
        return this.getId() + "," + this.getMsg();
    }

}
