package com.example.kevinwidjaja.kitchenmanager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;


public class ShoppingListActivity extends ActionBarActivity {

    private Toolbar toolbar;
    private Toolbar toolbar_bottom;

    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

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
                        intent = new Intent(ShoppingListActivity.this, InventoryActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.shoppinglist:
                        Toast.makeText(getApplicationContext(), "Shopping List", Toast.LENGTH_SHORT).show();
                        intent = new Intent(ShoppingListActivity.this, ShoppingListActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.recipe:
                        Toast.makeText(getApplicationContext(), "Recipe", Toast.LENGTH_SHORT).show();
                        intent = new Intent(ShoppingListActivity.this, RecipeActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.event:
                        Toast.makeText(getApplicationContext(), "Event", Toast.LENGTH_SHORT).show();
                        intent = new Intent(ShoppingListActivity.this, EventActivity.class);
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
        final ListView newsEntryListView = (ListView) findViewById(R.id.shoppinglist_listView);
        final InventoryAdapter inventoryAdapter = new InventoryAdapter(this, R.layout.activity_shopping_list_listitem);
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
                Toast.makeText(getApplicationContext(),
                        "clicked " + item + " Position: " + position , Toast.LENGTH_LONG)
                        .show();

                //bundle to pass value to another activity
                Bundle localbundle = new Bundle();
                localbundle.putString("name",pointer.getName());
                localbundle.putInt("id",pointer.getId());
                localbundle.putInt("quantity",pointer.getQuantity());
                localbundle.putInt("unit_id", pointer.getUnit_id());
                Intent intent = new Intent(ShoppingListActivity.this, ShoppingListActivity_edit.class);
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
                Toast.makeText(getApplicationContext(),
                        "long clicked " + item + " Position: " + position, Toast.LENGTH_LONG)
                        .show();

                //removing from adapter view only (not including item in database)
                //inventoryAdapter.remove(pointer);

                //removing from database
                //db.deleteInventory(pointer.getId());

                return true;
            }
        });


        /*
        // NOT IMPLEMENTED YET
        //SearchBox implementation
        InventoryAdapter adapterSearch = new InventoryAdapter(this, android.R.layout.simple_dropdown_item_1line);

        AutoCompleteTextView searchItems = (AutoCompleteTextView) findViewById(R.id.searchItem);
        searchItems.setAdapter(adapterSearch);
        /////////////
        */

        //SearchBox implementation
        /*
        ArrayAdapter<String> adapterSearch = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, values);

        AutoCompleteTextView searchItems = (AutoCompleteTextView) findViewById(R.id.searchItem);
        searchItems.setAdapter(adapterSearch);


        String selectQuery = "SELECT * FROM "
                + "inventories";
        Cursor searchCursor = db.rawQuery(selectQuery);
        CursorAdapter adapterSearch = new SimpleCursorAdapter(this,searchCursor,true);
        SearchView searchItems = (SearchView) findViewById(R.id.searchItem);
        searchItems.setSuggestionsAdapter(adapterSearch);

        */
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shopping_list, menu);
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
        /*
        // mock Entry for testing
        final List<Inventory> entries = new ArrayList<Inventory>();

        for(int i = 1; i < 50; i++) {
            entries.add(new Inventory(i,"Test Entry " + i, i, i));
        }
        */
        db.closeDB();
        return entries;
    }

    /**
     * add shopping list button
     */
    public void addShoppingList(View view) {
        Toast.makeText(getApplicationContext(), "Add Shopping List", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, ShoppingListActivity_add.class);
        startActivity(intent);
    }

}
