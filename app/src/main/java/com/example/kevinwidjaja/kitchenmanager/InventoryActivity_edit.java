package com.example.kevinwidjaja.kitchenmanager;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class InventoryActivity_edit extends ActionBarActivity {

    private Toolbar toolbar;
    private Toolbar toolbar_bottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_activity_edit);

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
                        intent = new Intent(InventoryActivity_edit.this, InventoryActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.shoppinglist:
                        Toast.makeText(getApplicationContext(), "Shopping List", Toast.LENGTH_SHORT).show();
                        intent = new Intent(InventoryActivity_edit.this, ShoppingListActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.recipe:
                        Toast.makeText(getApplicationContext(), "Recipe", Toast.LENGTH_SHORT).show();
                        intent = new Intent(InventoryActivity_edit.this, RecipeActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.event:
                        Toast.makeText(getApplicationContext(), "Event", Toast.LENGTH_SHORT).show();
                        intent = new Intent(InventoryActivity_edit.this, EventActivity.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });
        toolbar_bottom.inflateMenu(R.menu.menu_bottomnav);


        //get value from previous
        Integer newInventory_id_val = null;
        String newInventory_name_val = null;
        Integer newInventory_quantity_val = null;
        Integer newInventory_unit_val = null;

        newInventory_id_val = getIntent().getIntExtra("id",0);
        newInventory_name_val = getIntent().getStringExtra("name");
        newInventory_quantity_val = getIntent().getIntExtra("quantity",0);
        newInventory_unit_val = getIntent().getIntExtra("unit_id", 0);


        //populate the form with value
        EditText n_entry = (EditText) findViewById(R.id.inventoryname);
        EditText quantity_entry = (EditText) findViewById(R.id.inventoryquantity);
        EditText unitid_entry = (EditText) findViewById(R.id.inventoryunit);

        n_entry.setText(newInventory_name_val);
        quantity_entry.setText(newInventory_quantity_val.toString());
        unitid_entry.setText(newInventory_unit_val.toString());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inventory_activity_edit, menu);
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
     * editing entry
     * @param view
     */
    public void editEntry(View view) {


        Integer newInventory_id_val = null;
        String newInventory_name_val = null;
        Integer newInventory_quantity_val = null;
        Integer newInventory_unit_val = null;

        newInventory_id_val = getIntent().getIntExtra("id",0);
        newInventory_name_val = getIntent().getStringExtra("name");
        newInventory_quantity_val = getIntent().getIntExtra("quantity",0);
        newInventory_unit_val = getIntent().getIntExtra("unit_id", 0);

        Toast.makeText(getApplicationContext(), "edit Inventory : " + newInventory_id_val + ":" + newInventory_name_val + ":" + newInventory_quantity_val + ":" + newInventory_unit_val, Toast.LENGTH_SHORT).show();

        //consistency check before adding


    }
}
