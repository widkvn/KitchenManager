package com.example.kevinwidjaja.kitchenmanager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import java.util.ArrayList;
import java.util.List;


public class RecipeAddIngredient extends ActionBarActivity {

    private Toolbar toolbar;
    private Toolbar toolbar_bottom;

    Button addIngredientButton;
    Button doneButton;
    DBHelper db;
    List<String> stringInventoryList,listViewInventoryList;
    EditText ingredientName,qty,unit;
    //ListView
    ListView inventory_list;
    //Listview Adapter
    ArrayAdapter<String> inventorylist_adapter;

    List<Inventory> allInventory;
    List<RecipeInventory> allRecipeInventory;
    String recipe_id;
    int temp_recipe_id;
    long inventory_id;

    int is_new_ingredient;
    String ingredient_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_add_ingredient);

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
                        intent = new Intent(RecipeAddIngredient.this, InventoryActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.shoppinglist:
                        Toast.makeText(getApplicationContext(), "Shopping List", Toast.LENGTH_SHORT).show();
                        intent = new Intent(RecipeAddIngredient.this, ShoppingListActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.recipe:
                        Toast.makeText(getApplicationContext(), "Recipe", Toast.LENGTH_SHORT).show();
                        intent = new Intent(RecipeAddIngredient.this, RecipeActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.event:
                        Toast.makeText(getApplicationContext(), "Event", Toast.LENGTH_SHORT).show();
                        intent = new Intent(RecipeAddIngredient.this, EventActivity.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });
        toolbar_bottom.inflateMenu(R.menu.menu_bottomnav);

        is_new_ingredient=1;

        db = new DBHelper(this);

        //Getting temporary recipe id from recipe activity
        Bundle extras = getIntent().getExtras();
        recipe_id=extras.getString("Recipe Id");
        temp_recipe_id=Integer.parseInt(recipe_id);

        ingredientName=(EditText)findViewById(R.id.ingredientName);
        ingredientName.setVisibility(View.GONE);

        inventory_list = (ListView) findViewById(R.id.inventoryList);

        stringInventoryList=new ArrayList<String>();
        listViewInventoryList=new ArrayList<String>();
        allInventory = db.getAllInventories();



        //Populating spinner list with Inventory
        stringInventoryList.add("Add new ingredient");
        for (Inventory inventory : allInventory)
        {
            stringInventoryList.add(inventory.getName());
        }
        //Populating Ingredients listView with RecipeInventory contents.

        allRecipeInventory=db.getAllRecipeInventory();
        for (RecipeInventory recipeInventory : allRecipeInventory)
        {
            if(recipeInventory.getRecipe_id()==temp_recipe_id)
            {
                //Get inventory id from  RecipeInventory that corresponds to this recipe's id
                //Go through allInventory and wherever you find the inventory id, return the inventory name.
                for (Inventory inventory : allInventory)
                {
                    if(inventory.getId()==recipeInventory.getInventory_id())
                    {
                        listViewInventoryList.add(inventory.getName());
                    }
                }
            }

        }

        inventorylist_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1,listViewInventoryList );
        inventory_list.setAdapter(inventorylist_adapter);
        inventorylist_adapter.notifyDataSetChanged();

        //On Long Click, delete item in list
        inventory_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,int pos, long id)
            {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(), "Pos selected" + pos, Toast.LENGTH_LONG).show();

                final int position=pos;
                //Make an alert dialog box appear
                AlertDialog.Builder delete_ingredient=new AlertDialog.Builder(RecipeAddIngredient.this);

                delete_ingredient.setMessage("Delete Ingredient?");
                delete_ingredient.setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int whichButton)
                    {

                        yes(position);
                    }
                });
                delete_ingredient.setNegativeButton("No", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int whichButton)
                    {

                        no();
                    }
                });
                delete_ingredient.show();

                return true;
            }
        });

        //Toast.makeText(getApplicationContext(), stringInventoryList.get(0).toString(), Toast.LENGTH_LONG).show();

        //Add Ingredient to DB Button
        addIngredientButton = (Button) findViewById(R.id.addIngredientButton);
        addIngredientButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                boolean success;
                success=AddIngredient(is_new_ingredient,ingredient_name);

            }
        });

        //Done with adding ingredients Button
        doneButton = (Button) findViewById(R.id.DoneButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                finish();
            }
        });


        Spinner spinner = (Spinner) findViewById(R.id.ingredientNames);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, stringInventoryList); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setOnItemSelectedListener(new OnItemSelectedListener()
        {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3)
            {

                //RelativeLayout rl=(RelativeLayout) findViewById(R.id.lv_subtask);
                // TODO Auto-generated method stub
                if(arg2==0)
                {
                    is_new_ingredient=1;
                    Toast.makeText(getApplicationContext(), "Clicked on first item !", Toast.LENGTH_LONG).show();
                    ingredientName.setVisibility(View.VISIBLE);
                    
                }
                else
                {
                    is_new_ingredient=0;
                    ingredient_name=stringInventoryList.get(arg2);
                    ingredientName.setVisibility(View.GONE);
                }
            }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub

        }
    });
        db.close();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recipe_add_ingredient, menu);
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
    public boolean AddIngredient(int new_ingredient, String ingredientname)
    {

        inventory_id=0;
        int found_ingredient=0;

        String name="", quantity="",unt="";
        db = new DBHelper(this);

        qty=(EditText)findViewById(R.id.ingredientQty);
        unit=(EditText)findViewById(R.id.ingredientUnit);
        if(new_ingredient==1)
            name=ingredientName.getText().toString();
        else
            name=ingredientname;

        quantity=qty.getText().toString();
        unt=unit.getText().toString();

        //Check if ingredient name entered already exists in inventory table
        allInventory = db.getAllInventories();
        for (Inventory inventory : allInventory)
        {
            //If entered ingredient name is already present in inventory
            if (inventory.getName().equals(name))
            {
                inventory_id = inventory.getId();
                found_ingredient = 1;
                break;
            }
        }
            //Else, ingredient is created as a new row in Inventory table
        if(found_ingredient ==0)
            {
                Inventory new_inventory = new Inventory(name,Integer.parseInt(unt),0);
                inventory_id=db.createInventory(new_inventory);
            }
            //Inventory row has been updated/created. Now, creating inventory-recipe row.
            RecipeInventory recipeInventory = new RecipeInventory(temp_recipe_id,(int)inventory_id,Integer.parseInt(quantity));
            long recipeInventory_id = db.createRecipeInventory(recipeInventory);

        db.close();

        refreshAddIngredientPage();
        return true;
    }
    void refreshAddIngredientPage()
    {
        //Clear all EditText fields
        ingredientName.setText("");
        qty.setText("");
        unit.setText("");
        refreshIngredientListView();
        db.close();
    }
    public void yes(int pos)
    {
        db = new DBHelper(this);
        allInventory = db.getAllInventories();
        db.deleteInventory(allInventory.get(pos).getId());
        db.close();
        refreshIngredientListView();
    }
    public void no()
    {


    }
    //Refresh List View from Inventory Recipe page
    public void refreshIngredientListView()
    {
        db = new DBHelper(this);

        //Populating Ingredients listView with RecipeInventory contents.

        allRecipeInventory=db.getAllRecipeInventory();
        listViewInventoryList.clear();
        for (RecipeInventory recipeInventory : allRecipeInventory)
        {
            if(recipeInventory.getRecipe_id()==temp_recipe_id)
            {
                //Get inventory id from  RecipeInventory that corresponds to this recipe's id
                //Go through allInventory and wherever you find the inventory id, return the inventory name.
                for (Inventory inventory : allInventory)
                {
                    if(inventory.getId()==recipeInventory.getInventory_id())
                    {
                        listViewInventoryList.add(inventory.getName());
                    }
                }
            }

        }
        inventorylist_adapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1,listViewInventoryList );
        inventory_list.setAdapter(inventorylist_adapter);
        inventorylist_adapter.notifyDataSetChanged();
        /*
        //Refresh Ingredient List View
        allInventory = db.getAllInventories();
        listViewInventoryList.clear();
        for (Inventory inventory : allInventory)
        {
            listViewInventoryList.add(inventory.getName());
        }
        inventorylist_adapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1,listViewInventoryList );
        inventory_list.setAdapter(inventorylist_adapter);
        inventorylist_adapter.notifyDataSetChanged();
        */
        db.close();
    }

}
