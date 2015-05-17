package com.example.kevinwidjaja.kitchenmanager;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class EventViewEventActivity extends ActionBarActivity
{
    List<EventRecipe> allEventRecipe;
    List<Recipe> allRecipe;
    DBHelper db;

    List<Integer> allRecipeIds;
    List<String>  allRecipeNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_view_event);

        Bundle bundle = getIntent().getExtras();
        String id=bundle.getString("EventID");

        //getting EventRecipe content
        db=new DBHelper(this);
        allRecipeIds=new ArrayList<Integer>();
        allRecipeNames=new ArrayList<String>();
        allEventRecipe = db.getAllEventRecipe();
        for (EventRecipe eventRecipe : allEventRecipe)
        {
            if(eventRecipe.getEvent_id()==Integer.parseInt(id))//If event id is found in EventRecipe table
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
        //populate List View
        populate_list_view();
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
