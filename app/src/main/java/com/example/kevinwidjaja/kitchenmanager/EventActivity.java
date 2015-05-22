package com.example.kevinwidjaja.kitchenmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class EventActivity extends ActionBarActivity {

    private Toolbar toolbar;
    private Toolbar toolbar_bottom;

    CalendarView calendar;
    Calendar calendar_var;
    int chosen_year,chosen_month,chosen_day;
    Date date;
    SimpleDateFormat sdf1;
    SimpleDateFormat sdf2;
    String sDate,sDate2;


    DBHelper db;
    List<Event> allEvent;

    List<String> event_names;

    long event_id;//to store the event id that gets passed to EventViewActivity


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

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
                        intent = new Intent(EventActivity.this, InventoryActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.shoppinglist:
                        Toast.makeText(getApplicationContext(), "Shopping List", Toast.LENGTH_SHORT).show();
                        intent = new Intent(EventActivity.this, ShoppingListActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.recipe:
                        Toast.makeText(getApplicationContext(), "Recipe", Toast.LENGTH_SHORT).show();
                        intent = new Intent(EventActivity.this, RecipeActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.event:
                        Toast.makeText(getApplicationContext(), "Event", Toast.LENGTH_SHORT).show();
                        intent = new Intent(EventActivity.this, EventActivity.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });
        toolbar_bottom.inflateMenu(R.menu.menu_bottomnav);

        //Log contents of Events table
        db=new DBHelper(this);
        List<Event> allEvent = db.getAllEvent();
        for (Event event : allEvent){
            Log.d("Event Content", event.toString());        }
        List<Event> allEventRecipe = db.getAllEvent();
        for (Event eventrecipe : allEventRecipe){
            Log.d("Event Recipe Content", eventrecipe.toString());        }

        db.close();

        date= new Date(System.currentTimeMillis());
        sdf1 = new SimpleDateFormat("MM/dd/yyyy");
        sDate= sdf1.format(date);

        //initializes the calendarview
        initializeCalendar();

        // populating list view
        populate_list_view();

        //On clicking Add Button
        addNewEvent();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event, menu);
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

    //initializes the calendarview
    public void initializeCalendar()
    {
        calendar = (CalendarView) findViewById(R.id.calendarView);

        // sets whether to show the week number.
        calendar.setShowWeekNumber(false);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
        //show the selected date as a toast
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int day)
            {
                //month=month+1;
                chosen_year=year;
                chosen_month=month;
                chosen_day=day;
                calendar_var = Calendar.getInstance();
                calendar_var.clear();
                calendar_var.set(year,month,day);
                date = calendar_var.getTime();
                sDate= sdf1.format(date);
                Toast.makeText(getApplicationContext(), day + "/" + month + "/" + year, Toast.LENGTH_LONG).show();
                populate_list_view();

            }
    });
    }
    void populate_list_view()
    {
       ListView listview = (ListView) findViewById(R.id.list_view);

        event_names=new ArrayList<String>();
        /*
        String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
                "Blackberry", "Button","Boxes" };
        */
        db=new DBHelper(this);
        allEvent = db.getAllEvent();
        Log.v("EventActivity date",sDate);
        for (Event event : allEvent)
        {
            sdf2 = new SimpleDateFormat("MM/dd/yyyy");
            sDate2= sdf1.format(event.getDate());
            Log.v("EventActivityEventDate",sDate2);
            if(date.compareTo(event.getDate())==0)
            {
                event_names.add(event.getTitle());

            }
        }

        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, event_names);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id)
            {
                final String item = (String) parent.getItemAtPosition(position);
                //Find event id of event selected
                db=new DBHelper(EventActivity.this);
                allEvent = db.getAllEvent();
                for (Event event : allEvent)
                {
                    if( (item.equals(event.getTitle())) && (event.getDate().compareTo(date)==0) )
                    {
                        event_id=event.getId();
                        break;
                    }
                }
                db.close();
                //Carry over event_id to next activity
                Intent myIntent2 = new Intent(EventActivity.this, EventViewEventActivity.class);
                myIntent2.putExtra("EventID",String.valueOf(event_id));
                EventActivity.this.startActivity(myIntent2);

                //Toast.makeText(getApplicationContext(),item, Toast.LENGTH_LONG).show();
            }

        });
    }

    public void addNewEvent()
    {
        ImageButton recipe_button = (ImageButton) findViewById(R.id.imageButton1);
        recipe_button.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                // Perform action on click
                     Intent myIntent = new Intent(EventActivity.this, EventAddActivity.class);
                     //myIntent.putExtra("Recipe Id",str_temprecipe_id);


                // Creating Bundle object
                Bundle b = new Bundle();
                b.putInt("year", chosen_year);
                b.putInt("month", chosen_month);
                b.putInt("day", chosen_day);
                myIntent.putExtras(b);
                startActivity(myIntent);

                //EventActivity.this.startActivity(myIntent);
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        populate_list_view();
    }
}
