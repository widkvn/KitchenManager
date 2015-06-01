package com.example.kevinwidjaja.kitchenmanager;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


public class EventAddActivity extends ActionBarActivity {

    private Toolbar toolbar;
    private Toolbar toolbar_bottom;

    MyCustomAdapter dataAdapter = null;

    EditText eventName;
    Button addEventButton;
    Button cancelEventButton;

    DBHelper db;
    List<Recipe> allRecipe;

    int day,month,year;
    Date date;

    String name;

    Event new_event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_add);

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
                        intent = new Intent(EventAddActivity.this, InventoryActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.shoppinglist:
                        Toast.makeText(getApplicationContext(), "Shopping List", Toast.LENGTH_SHORT).show();
                        intent = new Intent(EventAddActivity.this, ShoppingListActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.recipe:
                        Toast.makeText(getApplicationContext(), "Recipe", Toast.LENGTH_SHORT).show();
                        intent = new Intent(EventAddActivity.this, RecipeActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.event:
                        Toast.makeText(getApplicationContext(), "Event", Toast.LENGTH_SHORT).show();
                        intent = new Intent(EventAddActivity.this, EventActivity.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });
        toolbar_bottom.inflateMenu(R.menu.menu_bottomnav);

        //Get Bundle values
        Intent in = getIntent();
        Bundle b = in.getExtras();
        day = b.getInt("day");
        month = b.getInt("month");
        year = b.getInt("year");
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(year,month,day);
        date = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        String sDate= sdf.format(date);
        Log.v("Date",sDate);

        //Generate list View from ArrayList
        displayListView();

        //Add Event to DB Button
        addEvent();

        //Cancel Event addition
        cancelEventButton = (Button) findViewById(R.id.cancelEventButton);
        cancelEventButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                // Perform action on click
                boolean success;
                finish();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event_add, menu);
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
    private void displayListView()
    {

        //Array list of recipes
        ArrayList<RecipeItemInEvent> recipeList = new ArrayList<RecipeItemInEvent>();
        //New- Retrieve recipe list from DB

         db = new DBHelper(this);
         RecipeItemInEvent recipe ;
         allRecipe = db.getAllRecipe();
          for (Recipe recipeitem : allRecipe)
          {
            recipe=new RecipeItemInEvent(recipeitem.getName(),false);
           recipeList.add(recipe);
          }
         db.close();

        //create an ArrayAdaptar from the String Array
        dataAdapter = new MyCustomAdapter(this,
                R.layout.events_recipe_listitem, recipeList);
        ListView listView = (ListView) findViewById(R.id.list_view);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id)
            {
                // When clicked, show a toast with the TextView text
                RecipeItemInEvent country = (RecipeItemInEvent) parent.getItemAtPosition(position);
                /*Toast.makeText(getApplicationContext(),
                        "Clicked on Row: " + country.getName(),
                        Toast.LENGTH_LONG).show();*/
            }
        });

    }

    private class MyCustomAdapter extends ArrayAdapter<RecipeItemInEvent>
    {

        private ArrayList<RecipeItemInEvent> recipeList;

        public MyCustomAdapter(Context context, int textViewResourceId,
                               ArrayList<RecipeItemInEvent> countryList)
        {
            super(context, textViewResourceId, countryList);
            this.recipeList = new ArrayList<RecipeItemInEvent>();
            this.recipeList.addAll(countryList);
        }

        private class ViewHolder
        {
            TextView code;
            CheckBox name;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            //Log.v("ConvertView", String.valueOf(position));

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.events_recipe_listitem, null);

                holder = new ViewHolder();
                holder.code = (TextView) convertView.findViewById(R.id.code);
                holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
                convertView.setTag(holder);

                holder.name.setOnClickListener( new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v ;
                        RecipeItemInEvent country = (RecipeItemInEvent) cb.getTag();
                        /*Toast.makeText(getApplicationContext(),
                                "Clicked on Checkbox: " + cb.getText() +
                                        " is " + cb.isChecked(),
                                Toast.LENGTH_LONG).show();*/
                        country.setSelected(cb.isChecked());
                    }
                });
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }

            RecipeItemInEvent country = recipeList.get(position);
            //holder.code.setText(" (" +  country.getCode() + ")");
            holder.name.setText(country.getName());
            holder.name.setChecked(country.isSelected());
            holder.name.setTag(country);
            return convertView;
        }
    }

    public void addEvent()
    {


        db= new DBHelper(EventAddActivity.this);

        //ArrayList to store checked RecipeIDs
        final List<Integer> recipe_id_list = new ArrayList<Integer>();

        addEventButton = (Button) findViewById(R.id.addEventButton);
        addEventButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                // Perform action on click
                //Add event name & recipes to an event
                eventName=(EditText)findViewById(R.id.eventName);
                name=eventName.getText().toString();

                StringBuffer responseText = new StringBuffer();
                responseText.append("The following were selected...\n");
                ArrayList<RecipeItemInEvent> eventRecipeList = dataAdapter.recipeList;
                for (int i = 0; i < eventRecipeList.size(); i++)
                {
                    RecipeItemInEvent recipe_item = eventRecipeList.get(i);
                    if (recipe_item.isSelected())
                    {
                        responseText.append("\n" + recipe_item.getName());
                        //Go to DB and find recipe id corresponding to this name

                        allRecipe = db.getAllRecipe();
                        for (Recipe recipeitem : allRecipe)
                        {
                            if (recipeitem.getName().equals(recipe_item.getName()))//If checked recipe name is found, store its recipe id
                            {
                                recipe_id_list.add(recipeitem.getId());
                            }
                        }

                    }
                }
                //Add Event to DB
                new_event = new Event(name,date, 1, 1);
                //inserting Event to database
                long event_id = db.createEvent(new_event);
                long eventrecipe_id;

                Iterator<Integer> Iterator = recipe_id_list.iterator();
                while (Iterator.hasNext())
                {
                    //creating eventRecipe
                    EventRecipe eventRecipe = new EventRecipe((int)event_id,Iterator.next());
                    eventrecipe_id = db.createEventRecipe(eventRecipe);
                }
                //getting all EventRecipe content
                Log.d("Get EventRecipe", "Get All EventRecipe");
                List<EventRecipe> allEventRecipe = db.getAllEventRecipe();
                for (EventRecipe eventRecipe : allEventRecipe)
                {
                    Log.d("EventRecipe Content", eventRecipe.toString());
                }


                /*Toast.makeText(getApplicationContext(),
                        responseText, Toast.LENGTH_LONG).show();*/
                Log.v("EventAdd","After toast");
                db.close();
                Log.v("EventAdd","Db closed");
                finish();
                Log.v("EventAdd","After finish");

            }
        });
        db.close();
    }


}
