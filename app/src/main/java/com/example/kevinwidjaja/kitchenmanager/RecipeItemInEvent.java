package com.example.kevinwidjaja.kitchenmanager;

/**
 * Created by Owner on 5/15/2015.
 */
public class RecipeItemInEvent
{
    String name=null;
    boolean selected=false;
    public RecipeItemInEvent(String name, boolean selected) {
        super();

        this.name = name;
        this.selected = selected;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
