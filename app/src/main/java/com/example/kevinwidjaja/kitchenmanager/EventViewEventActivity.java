package com.example.kevinwidjaja.kitchenmanager;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class EventViewEventActivity extends ActionBarActivity
{
    private Toolbar toolbar;
    private Toolbar toolbar_bottom;

    List<EventRecipe> allEventRecipe;
    List<Recipe> allRecipe;
    DBHelper db;

    List<Integer> allRecipeIds;
    List<String>  allRecipeNames;

    int event_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_view_event);

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
                        intent = new Intent(EventViewEventActivity.this, InventoryActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.shoppinglist:
                        Toast.makeText(getApplicationContext(), "Shopping List", Toast.LENGTH_SHORT).show();
                        intent = new Intent(EventViewEventActivity.this, ShoppingListActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.recipe:
                        Toast.makeText(getApplicationContext(), "Recipe", Toast.LENGTH_SHORT).show();
                        intent = new Intent(EventViewEventActivity.this, RecipeActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.event:
                        Toast.makeText(getApplicationContext(), "Event", Toast.LENGTH_SHORT).show();
                        intent = new Intent(EventViewEventActivity.this, EventActivity.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });
        toolbar_bottom.inflateMenu(R.menu.menu_bottomnav);

        Bundle bundle = getIntent().getExtras();
        String id=bundle.getString("EventID");
        event_id=Integer.parseInt(id);

        //getting EventRecipe content
        db=new DBHelper(this);
        allRecipeIds=new ArrayList<Integer>();
        allRecipeNames=new ArrayList<String>();
        allEventRecipe = db.getAllEventRecipe();
        for (EventRecipe eventRecipe : allEventRecipe)
        {
            if(eventRecipe.getEvent_id()==event_id)//If event id is found in EventRecipe table
            {
                allRecipeIds.add(eventRecipe.getRecipe_id());
            }
        }
        //getting Recipe content
        allRecipe = db.getAllRecipe();
        for (Recipe recipe : allRecipe)
        {
            for(int i=0;i<allRecipeIds.size();i++)
            {
                if(recipe.getId()==allRecipeIds.get(i))
                    allRecipeNames.add(recipe.getName());
            }
        }
        db.close();
        //populate List View
        populate_list_view();

        //Delete Recipe button
        ImageButton delete_event_button = (ImageButton) findViewById(R.id.deleteEventButton);
        delete_event_button.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                // Perform action on click
                db=new DBHelper(EventViewEventActivity.this);

                //Delete corresponding entries in EventRecipe table
                allEventRecipe = db.getAllEventRecipe();

                for (EventRecipe eventRecipe : allEventRecipe)
                {
                    if(eventRecipe.getEvent_id()==event_id)//If event id is found in EventRecipe table
                    {
                        db.deleteEventRecipe(eventRecipe.getId());
                    }
                }

                //Delete entry from Event table
                db.deleteEvent((long)event_id);
                db.close();
                finish();

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event_view_event, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void populate_list_view()
    {
        ListView listview = (ListView) findViewById(R.id.list_view);
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, allRecipeNames);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id)
            {

            }
        });

    }
}
