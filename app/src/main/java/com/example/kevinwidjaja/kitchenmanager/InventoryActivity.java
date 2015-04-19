package com.example.kevinwidjaja.kitchenmanager;


import android.app.Dialog;
import android.content.Context;
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

    //go to form button
    private ImageView addInventory;

    //used for add inventory form
    final Context context = this;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //DB
        db = new DBHelper(this);

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


        // ListView
        final ListView listview = (ListView) findViewById(R.id.listView);

        //Extracting all information retained in database
        final List<Inventory> allInventory = db.getAllInventories();
        final HashMap<String, Integer> allInventoryMaps = new HashMap<String, Integer>();

        //list for generating listview
        final ArrayList<String> list = new ArrayList<String>();
        Iterator<Inventory> it = allInventory.iterator();

        while(it.hasNext()) {
            Inventory current = it.next();
            String inv_name = current.getName();
            list.add(inv_name);
            allInventoryMaps.put(inv_name, current.getId());
        }

        final StableArrayAdapter adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                final String item = (String) parent.getItemAtPosition(position);

                //getting inventory ID from HashMap
                int invID = allInventoryMaps.get(item);
                db.deleteInventory(invID);
                list.remove(item);

                adapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(),
                        "Delete " + item + " ID: " + invID , Toast.LENGTH_LONG)
                        .show();


            }
        });


        //Inventory add Button
        addInventory = (ImageView) findViewById(R.id.addinventory);

        // add button listener
        addInventory.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // custom dialog
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.activity_inventory_add);
                dialog.setTitle("Add New Inventory");

                final TextView newInventory_name = (TextView) dialog.findViewById(R.id.inventoryname);
                final TextView newInventory_quantity = (TextView) dialog.findViewById(R.id.inventoryquantity);
                final TextView newInventory_unit = (TextView) dialog.findViewById(R.id.inventoryunit);

                /*
                // set the custom dialog components - text, image and button - Edit Related
                TextView text = (TextView) dialog.findViewById(R.id.inventoryname);
                text.setText("Android custom dialog example!");
                ImageView image = (ImageView) dialog.findViewById(R.id.image);
                image.setImageResource(R.drawable.test);
                */

                ImageView dialogButton = (ImageView) dialog.findViewById(R.id.addInventorypop);
                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String newInventory_name_val = null;
                        Integer newInventory_quantity_val = null;
                        Integer newInventory_unit_val = null;

                        newInventory_name_val = newInventory_name.getText().toString();
                        newInventory_quantity_val = Integer.parseInt(newInventory_quantity.getText().toString());
                        newInventory_unit_val = Integer.parseInt(newInventory_unit.getText().toString());


                        // Uniqueness check not yet implemented ************
                        if(newInventory_name_val != null && newInventory_quantity_val >= 0 && newInventory_unit_val > 0){
                            //Adding new Inventory
                            db.createInventory(new Inventory(newInventory_name_val,newInventory_unit_val,newInventory_quantity_val));
                            adapter.notifyDataSetChanged();
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Added Inventory : " + newInventory_name.getText() + ":" + newInventory_quantity.getText() + ":" + newInventory_unit.getText(), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(InventoryActivity.this, InventoryActivity.class));
                        }else {
                            Toast.makeText(getApplicationContext(), "invalid input", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                dialog.show();
            }
        });

        //SearchBox implementation

        /*
        ArrayAdapter<String> adapterSearch = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, values);

        AutoCompleteTextView searchItems = (AutoCompleteTextView) findViewById(R.id.searchItem);
        searchItems.setAdapter(adapterSearch);
        */


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

    /**
     * Add Button action
     * @param view
     */
    public void addInventory(View view) {
        Toast.makeText(getApplicationContext(), "Add", Toast.LENGTH_SHORT).show();
    }


    /**
     * Listview related helper function
     */
    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }

}
