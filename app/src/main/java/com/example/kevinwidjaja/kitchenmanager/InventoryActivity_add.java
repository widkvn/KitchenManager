package com.example.kevinwidjaja.kitchenmanager;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class InventoryActivity_add extends ActionBarActivity {

    private Toolbar toolbar;
    private Toolbar toolbar_bottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_activity_add);

        // Main Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Bottom Toolbar
        toolbar_bottom = (Toolbar) findViewById(R.id.toolbar_bottom);
        toolbar_bottom.setTitle("Nav");
        toolbar_bottom.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent;
                switch(item.getItemId()){
                    case R.id.inventory:
                        Toast.makeText(getApplicationContext(), "Inventory", Toast.LENGTH_SHORT).show();
                        intent = new Intent(InventoryActivity_add.this, InventoryActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.shoppinglist:
                        Toast.makeText(getApplicationContext(), "Shopping List", Toast.LENGTH_SHORT).show();
                        intent = new Intent(InventoryActivity_add.this, ShoppingListActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.recipe:
                        Toast.makeText(getApplicationContext(), "Recipe", Toast.LENGTH_SHORT).show();
                        intent = new Intent(InventoryActivity_add.this, RecipeActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.event:
                        Toast.makeText(getApplicationContext(), "Event", Toast.LENGTH_SHORT).show();
                        intent = new Intent(InventoryActivity_add.this, EventActivity.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });
        toolbar_bottom.inflateMenu(R.menu.menu_bottomnav);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inventory_activity_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.home:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.inventory:
                intent = new Intent(this, InventoryActivity.class);
                startActivity(intent);
                break;
            case R.id.shoppinglist:
                intent = new Intent(this, ShoppingListActivity.class);
                startActivity(intent);
                break;
            case R.id.recipe:
                intent = new Intent(this, RecipeActivity.class);
                startActivity(intent);
                break;
            case R.id.event:
                intent = new Intent(this, EventActivity.class);
                startActivity(intent);
                break;
            case R.id.action_settings:
                // TODO
                break;
        }
        return true;
    }

    /**
     * adding entry
     * @param view
     */
    public void addEntry(View view) {

        //extract information from form
        final TextView newInventory_name = (TextView) findViewById(R.id.inventoryname);
        final TextView newInventory_quantity = (TextView) findViewById(R.id.inventoryquantity);
        final TextView newInventory_unit = (TextView) findViewById(R.id.inventoryunit);
        String newInventory_name_val = null;
        Integer newInventory_quantity_val = null;
        Integer newInventory_unit_val = null;

        //consistency check before adding
        //name check
        newInventory_name_val = newInventory_name.getText().toString();
        if(newInventory_name_val == null || newInventory_name_val.isEmpty()) {
            Toast.makeText(getApplicationContext(), "invalid name", Toast.LENGTH_SHORT).show();
            return;
        }

        //quantity check
        String check = newInventory_quantity.getText().toString();
        if(check.isEmpty()) {
            Toast.makeText(getApplicationContext(), "unfilled quantity", Toast.LENGTH_SHORT).show();
            return;
        }
        newInventory_quantity_val = Integer.parseInt(newInventory_quantity.getText().toString());
        if(newInventory_quantity_val < 0) {
            Toast.makeText(getApplicationContext(), "invalid quantity", Toast.LENGTH_SHORT).show();
            return;
        }

        //unit check
        check = newInventory_unit.getText().toString();
        if(check.isEmpty()) {
            Toast.makeText(getApplicationContext(), "unfilled unit", Toast.LENGTH_SHORT).show();
            return;
        }
        newInventory_unit_val = Integer.parseInt(newInventory_unit.getText().toString());
        if(newInventory_unit_val <= 0) {
            Toast.makeText(getApplicationContext(), "invalid unit", Toast.LENGTH_SHORT).show();
            return;
        }
        //////

        //Toast.makeText(getApplicationContext(), "Added Inventory : " + newInventory_name.getText() + ":" + newInventory_quantity.getText() + ":" + newInventory_unit.getText(), Toast.LENGTH_SHORT).show();

        //adding inventory
        Inventory inv = new Inventory(newInventory_name_val, newInventory_unit_val, newInventory_quantity_val);
        DBHelper db = new DBHelper(this);
        db.createInventory(inv);
        db.closeDB();
        //go back
        Intent intent = new Intent(this, InventoryActivity.class);
        startActivity(intent);
    }
}
