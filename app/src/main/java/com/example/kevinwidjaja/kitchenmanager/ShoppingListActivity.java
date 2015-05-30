package com.example.kevinwidjaja.kitchenmanager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
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
import java.util.Iterator;
import java.util.List;


public class ShoppingListActivity extends ActionBarActivity {

    private Toolbar toolbar;
    private Toolbar toolbar_bottom;

    DBHelper db;

    // List view
    private ListView lv;
    // Listview Adapter
    ArrayAdapter<String> adapter;
    List<Inventory> allShoppingListItems;
    List<UnitMeasure> allUnitMeasure;
    List<String> stringShoppingList;

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

        //ListView
        lv = (ListView) findViewById(R.id.list_view);

        // Adding items to listview
        populate_listview();
    }

    public void populate_listview()
    {
        Log.v("SLA","Hi");
        db= new DBHelper(this);
        allShoppingListItems=db.getAllInventories();
        allUnitMeasure=db.getAllUnitMeasure();
        stringShoppingList=new ArrayList<String>();
        int shop_qty=0;
        String unit_name="";
        for (Inventory inventory : allShoppingListItems)
        {
            Log.v("SLAInventory", inventory.toString());
            shop_qty=(inventory.getQuantity_req()-inventory.getQuantity());
            for(UnitMeasure unitMeasure : allUnitMeasure)
            {
                if(unitMeasure.getId()==inventory.getUnit_id())
                    unit_name =unitMeasure.getMetric();
            }
            if(shop_qty>0)
            {
                stringShoppingList.add(inventory.getName() + "\t\t\t"+String.valueOf(shop_qty) +" "+ unit_name);
            }
        }
        for(int i=0;i<stringShoppingList.size();i++)
        {

            Log.v("SLA", stringShoppingList.get(i));
        }

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1,stringShoppingList );
        lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        //Edit/View Shopping List
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id)
            {

            }
        });

        db.close();
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

  /*
    private List<Inventory> getInventoryEntries() {

        // Let's setup some test data.
        // Normally this would come from some asynchronous fetch into a data source
        // such as a sqlite database, or an HTTP request

        db = new DBHelper(this);
        final List<Inventory> entries = db.getAllInventories();
        final List<Inventory> entriesShopList = new ArrayList<Inventory>();
        Iterator<Inventory> it = entries.iterator();


        while(it.hasNext()) {
            target = it.next();
            if((target.getQuantity_req()-target.getQuantity()) > 0) {
                entriesShopList.add(target);
            }
        }

        db.closeDB();
        return entriesShopList;
    }
*/
    /**
     * add shopping list button
     */
    public void addShoppingList(View view) {
        //Toast.makeText(getApplicationContext(), "Add Shopping List", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, ShoppingListActivity_add.class);
        startActivity(intent);
    }

}
