package com.example.kevinwidjaja.kitchenmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class RecipeActivity extends ActionBarActivity {

    private Toolbar toolbar;
    private Toolbar toolbar_bottom;

    // List view
    private ListView lv;
    // Listview Adapter
    ArrayAdapter<String> adapter;
    // Search EditText
    EditText inputSearch;
    // ArrayList for Listview
    ArrayList<HashMap<String, String>> productList;
    //List for Recipes
    DBHelper db;
    List<Recipe> allRecipes;
    List<String> stringRecipeList;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

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
                        intent = new Intent(RecipeActivity.this, InventoryActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.shoppinglist:
                        Toast.makeText(getApplicationContext(), "Shopping List", Toast.LENGTH_SHORT).show();
                        intent = new Intent(RecipeActivity.this, ShoppingListActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.recipe:
                        Toast.makeText(getApplicationContext(), "Recipe", Toast.LENGTH_SHORT).show();
                        intent = new Intent(RecipeActivity.this, RecipeActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.event:
                        Toast.makeText(getApplicationContext(), "Event", Toast.LENGTH_SHORT).show();
                        intent = new Intent(RecipeActivity.this, EventActivity.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });
        toolbar_bottom.inflateMenu(R.menu.menu_bottomnav);

        //ListView
        lv = (ListView) findViewById(R.id.list_view);
        inputSearch = (EditText) findViewById(R.id.inputSearch);

        // Adding items to listview
        db= new DBHelper(this);
        allRecipes=db.getAllRecipe();
        stringRecipeList=new ArrayList<String>();
        for (Recipe recipe : allRecipes)
        {
            stringRecipeList.add(recipe.getName());
        }
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1,stringRecipeList );

        lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        //Search functionality
        inputSearch.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                RecipeActivity.this.adapter.getFilter().filter(cs);
            }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }
            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });

        //Add Recipe button
        ImageButton recipe_button = (ImageButton) findViewById(R.id.imageButton1);
        recipe_button.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                // Perform action on click
                Intent myIntent = new Intent(RecipeActivity.this, RecipeActivityAddRecipe.class);
                RecipeActivity.this.startActivity(myIntent);
                //Toast.makeText(getApplicationContext(), "Recipe", Toast.LENGTH_SHORT).show();
            }
        });


        //Edit/View Recipe
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                String item_id=String.valueOf(allRecipes.get(position).getId());

                //Toast.makeText(getApplicationContext(),"You clicked on Item"+item_pos, Toast.LENGTH_LONG).show();
                Intent myIntent2 = new Intent(RecipeActivity.this, RecipeActivityViewRecipe.class);
                myIntent2.putExtra("Item Id",item_id);
                RecipeActivity.this.startActivity(myIntent2);

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recipe, menu);
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
}
