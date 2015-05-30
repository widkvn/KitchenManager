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
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class EventActivity extends ActionBarActivity
{
    private Toolbar toolbar;
    private Toolbar toolbar_bottom;

    CalendarView calendar;
    Calendar calendar_var;
    int chosen_year,chosen_month,chosen_day;
    Date date,startDate,endDate;

    SimpleDateFormat sdf1;
    SimpleDateFormat sdf2;
    String sDate,sDate2,sStartDate,sEndDate;


    DBHelper db;
    List<Event> allEvent;
    List<EventRecipe> allEventRecipe;
    List<RecipeInventory> allRecipeInventory;
    List<Inventory> allInventory;


    List<String> event_names;

    long event_id;//to store the event id that gets passed to EventViewActivity

    //Update Shopping List variables
    List<Integer> weekly_eventIds;
    List<Integer> weekly_recipeIds;
    List<Integer> weekly_inventoryIds;
    List<Integer> weekly_inventoryQtys;

    Hashtable<Integer,Integer> itemAndQuantity = new Hashtable<Integer,Integer>();



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

        weekly_eventIds=new ArrayList<Integer>();
        weekly_recipeIds=new ArrayList<Integer>();
        weekly_inventoryIds=new ArrayList<Integer>();
        weekly_inventoryQtys=new ArrayList<Integer>();

        //Log contents of Events table
        db=new DBHelper(this);
        allEvent = db.getAllEvent();
        allEventRecipe = db.getAllEventRecipe();

        db.close();

        date=new Date(System.currentTimeMillis());
        startDate= new Date(System.currentTimeMillis());
        sdf1 = new SimpleDateFormat("MM/dd/yyyy");
        sStartDate= sdf1.format(startDate);

        //initializes the calendarview
        initializeCalendar();

        // populating list view
        populate_list_view();

        //On clicking Add Button
        addNewEvent();

        //On clicking generate shopping list button
        Button shopping_button = (Button) findViewById(R.id.generateSL);
        shopping_button.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                // Perform action on click
                generateSL();
            }
        });

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

    public void generateSL()
    {
        int new_qty=0;
        db=new DBHelper(this);
        allEvent = db.getAllEvent();
        allEventRecipe=db.getAllEventRecipe();
        allRecipeInventory = db.getAllRecipeInventory();
        allInventory = db.getAllInventories();

        weekly_eventIds.clear();
        weekly_recipeIds.clear();
        weekly_inventoryIds.clear();
        weekly_inventoryQtys.clear();
        itemAndQuantity.clear();

        date=new Date(System.currentTimeMillis());
        //Log all dates in the upcoming 7 days


        //Go through Events table. For each date which is between today and today=7, get id.
        for (Event event : allEvent)
        {

            if(isWithinRange(event.getDate()))
            {
                //Store event ids in EventIds List

                weekly_eventIds.add(event.getId());
            }
        }
        //Log check
        for(int i=0;i<weekly_eventIds.size();i++)
        {
            Log.v("EA weeklyEids",String.valueOf(weekly_eventIds.get(i)));
        }
        //For loop through EventRecipe table and retrieve and store in RecipeIdsList.
        for (EventRecipe eventRecipe : allEventRecipe)
        {
            if(weekly_eventIds.contains(eventRecipe.getEvent_id()))
            {
                weekly_recipeIds.add(eventRecipe.getRecipe_id());
            }

        }
        //Log check
        for(int i=0;i<weekly_recipeIds.size();i++)
        {
            Log.v("EventActivity Rids",String.valueOf(weekly_recipeIds.get(i)));
        }

        //For loop through RecipeInventory table and for each id=id in RecipeIdsList, create an entry InventoryID, Qty in Item hashtable
        for(int i=0;i<weekly_recipeIds.size();i++)
        {
            for (RecipeInventory recipeInventory : allRecipeInventory)
            {
                Log.v("RecipeInventory Content", recipeInventory.toString());
                if (weekly_recipeIds.get(i) == recipeInventory.getRecipe_id())
                {
                /*
                weekly_inventoryIds.add(recipeInventory.getInventory_id());
                weekly_inventoryQtys.add(recipeInventory.getQuantity());
                */
                    if (itemAndQuantity.containsKey(recipeInventory.getInventory_id()))
                    {
                        Log.v("EA", "Found Iid again");
                        new_qty = itemAndQuantity.get(recipeInventory.getInventory_id()) + recipeInventory.getQuantity();
                        itemAndQuantity.put(recipeInventory.getInventory_id(), new_qty);
                        new_qty = 0;
                    }
                    else
                    {
                        Log.v("EA", "Found Iid ");
                        itemAndQuantity.put(recipeInventory.getInventory_id(), recipeInventory.getQuantity());
                    }
                }
            }
        }
        //Log check
        Iterator it = itemAndQuantity.entrySet().iterator();
        while (it.hasNext())
        {
            Map.Entry pair = (Map.Entry) it.next();
            Log.v("EAitem&Qty key",String.valueOf(pair.getKey()));
            Log.v("EAitem&Qty value",String.valueOf(pair.getValue()));
        }
        //For loop through Inventory and for each item where InventoryID==id, make qty_req=Qty in Inventory-Qty in Item.
        int qty_req=0;

        for (Inventory inventory : allInventory)
        {
            if(itemAndQuantity.containsKey(inventory.getId()))
            {
                qty_req=itemAndQuantity.get(inventory.getId());
                inventory.setQuantity_req(qty_req);
                db.updateInventory(inventory);
            }
        }
        for (Inventory inventory : allInventory)
        {

            Log.v("EventActivity_qtyReq",String.valueOf(inventory.getQuantity_req()));
        }

    }

    //Function to check if date is within this upcoming week( 7 days including today )
    public boolean isWithinRange(Date testDate)
    {

        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        startDate = cal.getTime();
        cal.add(Calendar.DATE, 7); // 7 is the days you want to add or subtract
        endDate = cal.getTime();
        sEndDate= sdf1.format(endDate);
        String sTestDate=sdf1.format(testDate);
        Log.v("EA TestDate",sTestDate);
        Log.v("EA StartDate",sStartDate);
        Log.v("EA EndDate",sEndDate);
        Log.v("EA isAfterStart",String.valueOf(testDate.compareTo(startDate)));
        Log.v("EA isBeforeEnd",String.valueOf(testDate.compareTo(endDate))+"\n");
        return ( (testDate.compareTo(startDate)>=0) && (testDate.compareTo(endDate)<=0) );
        //return !(testDate.before(startDate) || testDate.after(endDate));
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

        for (Event event : allEvent)
        {
            sdf2 = new SimpleDateFormat("MM/dd/yyyy");
            sDate2= sdf1.format(event.getDate());

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
