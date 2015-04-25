package com.example.kevinwidjaja.kitchenmanager;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class RecipeActivityAddRecipe extends ActionBarActivity {

    Button addRecipeButton;
    Button addIngredientButton;
    DBHelper db;
    int temp_recipe_id;
    String recipe_id;

    List<Inventory> allInventory;
    List<RecipeInventory> allRecipeInventory;
    List<String> listViewInventoryList;

    TextView text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_activity_add_recipe);

        db = new DBHelper(this);

        //Getting temporary recipe id from recipe activity
        Bundle extras = getIntent().getExtras();
        recipe_id=extras.getString("Recipe Id");
        temp_recipe_id=Integer.parseInt(recipe_id);

        //Add Recipe to DB Button
        addRecipeButton = (Button) findViewById(R.id.addRecipeButton);
        addRecipeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                boolean success;
                success=AddRecipe();
                finish();
            }
        });

        //Cancel Recipe button
        Button cancelrecipe_button = (Button) findViewById(R.id.cancelRecipeButton);
        cancelrecipe_button.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                // Perform action on click

                db.deleteRecipe(temp_recipe_id);

                /*
                //Delete temporary recipe-inventory entries from recipeInventory table
                List<RecipeInventory> allRecipeInventory = db.getAllRecipeInventory();
                for (RecipeInventory recipeInventory : allRecipeInventory)
                {
                    if(recipeInventory.getRecipe_id()==temp_recipe_id)
                    {
                        db.deleteRecipeInventory(recipeInventory.getId());
                    }
                }
                */
                db.close();
                finish();
            }
        });


        //Go to Ingredient form Button
        addIngredientButton= (Button) findViewById(R.id.addIngredientButton);
        addIngredientButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent myIntent = new Intent(RecipeActivityAddRecipe.this, RecipeAddIngredient.class);
                myIntent.putExtra("Recipe Id",recipe_id);
                RecipeActivityAddRecipe.this.startActivity(myIntent);
            }
        });

       setTextView();
        db.close();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recipe_activity_add_recipe, menu);
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

    public boolean AddRecipe()
    {
        EditText recipeName,procedureField;

        String name="", procedure="";
        int serving=0;
        db = new DBHelper(this);

        recipeName=(EditText)findViewById(R.id.recipeName);
        procedureField=(EditText)findViewById(R.id.procedure);
        name=recipeName.getText().toString();
        procedure=procedureField.getText().toString();
        //Log.v("RecipeAddRecipeForm", name);

        Recipe recipe1 = new Recipe(temp_recipe_id,name,procedure,serving,1);

        //long recipe_id = db.createRecipe(recipe1);
        db.updateRecipe(recipe1);

        Log.d("Get Recipe", "Get All Recipe");
        List<Recipe> allRecipe = db.getAllRecipe();
        for (Recipe recipe : allRecipe){
            Log.d("Recipe Content", recipe.toString());
        }

        db.close();
        return true;
    }

    @Override
    public void onBackPressed()
    {
        db = new DBHelper(this);

        //Delete temporary recipe from recipe table
        db.deleteRecipe(temp_recipe_id);
        //Log test
        Log.d("Back Button", "Get All Recipe");
        List<Recipe> allRecipe = db.getAllRecipe();
        for (Recipe recipe : allRecipe){
            Log.d("Recipe Content", recipe.toString());
        }

        //Delete temporary recipe-inventory entries from recipeInventory table
        List<RecipeInventory> allRecipeInventory = db.getAllRecipeInventory();
        for (RecipeInventory recipeInventory : allRecipeInventory)
        {
            if(recipeInventory.getRecipe_id()==temp_recipe_id)
            {
                db.deleteRecipeInventory(recipeInventory.getId());
            }
        }
        //Log test
        Log.d("Back Button", "Get All RecipeInventory");
        allRecipeInventory = db.getAllRecipeInventory();
        for (RecipeInventory recipeInventory : allRecipeInventory)
        {
            Log.d("RecipeInventory", recipeInventory.toString());
        }

        db.close();
        finish();
    }
    public void setTextView()
    {
        //Set Text View to display list of ingredients already added to this recipe

        text = (TextView) findViewById(R.id.textView);
        listViewInventoryList=new ArrayList<String>();
        db = new DBHelper(this);
        Log.v("AddActivity","Here");
        allInventory=db.getAllInventories();
        allRecipeInventory=db.getAllRecipeInventory();
        Log.v("AddActivity","There");
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
                        Log.v("AddRecipe",inventory.getName());
                        listViewInventoryList.add(inventory.getName());
                    }
                }
            }

        }
        Log.v("AddActivity","There again");
        String textview_list="";
        for(String temp:listViewInventoryList)
        {
            textview_list=textview_list+temp+"\n";
            Log.v("AddRecipe",textview_list);
        }

        text.setText(textview_list);
        db.close();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        setTextView();
    }
}
