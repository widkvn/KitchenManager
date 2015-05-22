package com.example.kevinwidjaja.kitchenmanager;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class InventoryActivity extends ActionBarActivity {


    // For toolbar
    private Toolbar toolbar;
    private Toolbar toolbar_bottom;

    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

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
                        intent = new Intent(InventoryActivity.this, InventoryActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.shoppinglist:
                        Toast.makeText(getApplicationContext(), "Shopping List", Toast.LENGTH_SHORT).show();
                        intent = new Intent(InventoryActivity.this, ShoppingListActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.recipe:
                        Toast.makeText(getApplicationContext(), "Recipe", Toast.LENGTH_SHORT).show();
                        intent = new Intent(InventoryActivity.this, RecipeActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.event:
                        Toast.makeText(getApplicationContext(), "Event", Toast.LENGTH_SHORT).show();
                        intent = new Intent(InventoryActivity.this, EventActivity.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });
        toolbar_bottom.inflateMenu(R.menu.menu_bottomnav);

        //Initiate DB
        db = new DBHelper(this);

        //Display List View
        // Setup the list view
        final ListView newsEntryListView = (ListView) findViewById(R.id.inventory_listView);
        final InventoryAdapter inventoryAdapter = new InventoryAdapter(this, R.layout.activity_inventory_listitem);
        newsEntryListView.setAdapter(inventoryAdapter);

        // Populate the list, through the adapter
        for(final Inventory entry : getInventoryEntries()) {
            inventoryAdapter.add(entry);
        }

        // ListView Click listener; go to edit activity
        newsEntryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                final Inventory pointer = (Inventory) parent.getItemAtPosition(position);
                final String item = parent.getItemAtPosition(position).toString();
                /*
                Toast.makeText(getApplicationContext(),
                        "clicked " + item + " Position: " + position , Toast.LENGTH_LONG)
                        .show();
                        */

                //bundle to pass value to another activity
                Bundle localbundle = new Bundle();
                localbundle.putString("name",pointer.getName());
                localbundle.putInt("id",pointer.getId());
                localbundle.putInt("quantity",pointer.getQuantity());
                localbundle.putInt("unit_id",pointer.getUnit_id());
                Intent intent = new Intent(InventoryActivity.this, InventoryActivity_edit.class);
                intent.putExtras(localbundle);
                startActivity(intent);


            }
        });

        // ListView Long Click listener with remove implementation
        newsEntryListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {

                final Inventory pointer = (Inventory) parent.getItemAtPosition(position);
                final String item = parent.getItemAtPosition(position).toString();
                /*Toast.makeText(getApplicationContext(),
                        "long clicked " + item + " Position: " + position, Toast.LENGTH_LONG)
                        .show();*/

                //popup confirmation delete
                new AlertDialog.Builder(InventoryActivity.this)
                        .setTitle("Delete Inventory")
                        .setMessage("Do you really want to delete " + pointer.getName() + " ?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                Toast.makeText(InventoryActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                                //removing from database
                                //db.deleteInventory(pointer.getId());
                                pointer.setQuantity(0);
                                db.updateInventory(pointer);

                                //removing from adapter view only (not including item in database)
                                inventoryAdapter.remove(pointer);
                            }})
                        .setNegativeButton(android.R.string.no, null).show();

                return true;
            }
        });

        //Search

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inventory, menu);
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

    private List<Inventory> getInventoryEntries() {

        // Let's setup some test data.
        // Normally this would come from some asynchronous fetch into a data source
        // such as a sqlite database, or an HTTP request

        db = new DBHelper(this);
        final List<Inventory> entries = db.getAllInventories();
        final List<Inventory> entriesShopInv = new ArrayList<Inventory>();
        Iterator<Inventory> it = entries.iterator();
        while(it.hasNext()) {
            Inventory target = it.next();
            if(target.getQuantity() > 0) {
                entriesShopInv.add(target);
            }
        }
        /*
        // mock Entry for testing
        final List<Inventory> entries = new ArrayList<Inventory>();

        for(int i = 1; i < 50; i++) {
            entries.add(new Inventory(i,"Test Entry " + i, i, i));
        }
        */
        db.closeDB();
        return entriesShopInv;
    }

    /**
     * Add Button action
     * @param view
     */
    public void addInventory(View view) {
        //Toast.makeText(getApplicationContext(), "Add", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, InventoryActivity_add.class);
        startActivity(intent);
    }

}
