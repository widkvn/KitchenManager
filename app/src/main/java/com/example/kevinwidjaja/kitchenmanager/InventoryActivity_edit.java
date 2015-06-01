package com.example.kevinwidjaja.kitchenmanager;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Iterator;
import java.util.List;


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
        EditText unit_entry = (EditText) findViewById(R.id.inventoryunit);

        n_entry.setText(newInventory_name_val);
        quantity_entry.setText(newInventory_quantity_val.toString());

        // pull up unit from database
        DBHelper db = new DBHelper(this);
        String unit = db.getUnitMeasure((long) newInventory_unit_val).getMetric();
        unit_entry.setText(unit);

        db.closeDB();
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

        //Initialize
        Integer newInventory_id_val = null;
        String newInventory_name_val = null;
        Integer newInventory_quantity_val = null;
        String newInventory_unit_val = null;

        newInventory_id_val = getIntent().getIntExtra("id",0);
        /*
        newInventory_name_val = getIntent().getStringExtra("name");
        newInventory_quantity_val = getIntent().getIntExtra("quantity",0);
        newInventory_unit_val = getIntent().getIntExtra("unit_id", 0);
        */

        //get input from form
        final TextView newInventory_name = (TextView) findViewById(R.id.inventoryname);
        final TextView newInventory_quantity = (TextView) findViewById(R.id.inventoryquantity);
        final TextView newInventory_unit = (TextView) findViewById(R.id.inventoryunit);

        //consistency check before edit
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
        if(newInventory_quantity_val <= 0) {
            Toast.makeText(getApplicationContext(), "invalid quantity", Toast.LENGTH_SHORT).show();
            return;
        }

        //unit check
        newInventory_unit_val = newInventory_unit.getText().toString();
        if(newInventory_unit_val == null || newInventory_unit_val.isEmpty()) {
            Toast.makeText(getApplicationContext(), "invalid unit", Toast.LENGTH_SHORT).show();
            return;
        }
        //////

        //Toast.makeText(getApplicationContext(), "edit Inventory : " + newInventory_id_val + ":" + newInventory_name_val + ":" + newInventory_quantity_val + ":" + newInventory_unit_val, Toast.LENGTH_SHORT).show();

        //initiate db
        DBHelper db = new DBHelper(this);
        //check appropiate Unit
        List<UnitMeasure> umList = db.getAllUnitMeasure();
        Iterator<UnitMeasure> it = umList.iterator();
        boolean isExist = false;
        int idx = -1;
        while(it.hasNext()) {
            UnitMeasure target = it.next();
            if(target.getMetric().compareToIgnoreCase(newInventory_unit_val) == 0) {
                isExist = true;
                idx = target.getId();
            }
        }

        Inventory inv = null;
        //edit inventory
        if(isExist) { //if exist in database
           //Toast.makeText(getApplicationContext(), "Exist", Toast.LENGTH_SHORT).show();
            inv = db.getInventory(newInventory_id_val);

            inv.setName(newInventory_name_val);
            inv.setQuantity(newInventory_quantity_val);
            inv.setUnit_id(idx);
            db.updateInventory(inv);
        } else { // not exist create new UnitMeasure
            //Toast.makeText(getApplicationContext(), "not Exist", Toast.LENGTH_SHORT).show();
            inv = db.getInventory(newInventory_id_val);
            UnitMeasure um = new UnitMeasure(newInventory_unit_val);
            idx = (int) db.createUnitMeasure(um);

            inv.setName(newInventory_name_val);
            inv.setQuantity(newInventory_quantity_val);
            inv.setUnit_id(idx);
            db.updateInventory(inv);
        }

        db.closeDB();
        //go back
        /*
        Intent intent = new Intent(this, InventoryActivity.class);
        startActivity(intent);*/
        finish();

    }
}
