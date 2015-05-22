package com.example.kevinwidjaja.kitchenmanager;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;


public class RecipeActivityEditRecipe extends ActionBarActivity
{
    private Toolbar toolbar;
    private Toolbar toolbar_bottom;


    int recipe_id;
    DBHelper db=new DBHelper(this);
    Button updateRecipeButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_activity_edit_recipe);

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
                        intent = new Intent(RecipeActivityEditRecipe.this, InventoryActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.shoppinglist:
                        Toast.makeText(getApplicationContext(), "Shopping List", Toast.LENGTH_SHORT).show();
                        intent = new Intent(RecipeActivityEditRecipe.this, ShoppingListActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.recipe:
                        Toast.makeText(getApplicationContext(), "Recipe", Toast.LENGTH_SHORT).show();
                        intent = new Intent(RecipeActivityEditRecipe.this, RecipeActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.event:
                        Toast.makeText(getApplicationContext(), "Event", Toast.LENGTH_SHORT).show();
                        intent = new Intent(RecipeActivityEditRecipe.this, EventActivity.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });
        toolbar_bottom.inflateMenu(R.menu.menu_bottomnav);

        Bundle extras = getIntent().getExtras();
        String item_id=extras.getString("Recipe Id");
        recipe_id=Integer.parseInt(item_id);
        //Toast.makeText(getApplicationContext(), db.getRecipe(recipe_id).getName() + " " + db.getRecipe(recipe_id).getProcedure(), Toast.LENGTH_LONG).show();
        //Incomplete: Add rest of edittext buttons
        EditText recipe_name = (EditText) findViewById(R.id.recipeName);
        recipe_name.setText(db.getRecipe(recipe_id).getName());
        EditText procedure = (EditText) findViewById(R.id.procedure);
        procedure.setText(db.getRecipe(recipe_id).getProcedure());
        EditText serving = (EditText) findViewById(R.id.servings);
        serving.setText(String.valueOf(db.getRecipe(recipe_id).getServing()));

        //Update Recipe in DB Button
        updateRecipeButton = (Button) findViewById(R.id.updateRecipeButton);
        updateRecipeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                boolean success;
                success=UpdateRecipe();
                finish();
            }
        });

        //Cancel Update Recipe button
        Button cancelrecipe_button = (Button) findViewById(R.id.cancelRecipeButton);
        cancelrecipe_button.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                // Perform action on click
                finish();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recipe_activity_edit_recipe, menu);
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

    public boolean UpdateRecipe()
    {
        EditText recipeName,procedureField,servingField;
        //DBHelper db;
        Recipe recipe=new Recipe();
        String name="", procedure="";
        int serving=0;
        //db = new DBHelper(this);
        recipeName=(EditText)findViewById(R.id.recipeName);
        procedureField=(EditText)findViewById(R.id.procedure);
        servingField=(EditText)findViewById(R.id.servings);

        name=recipeName.getText().toString();
        procedure=procedureField.getText().toString();
        serving=Integer.parseInt(servingField.getText().toString());


        Log.v("RecipeEditRecipeForm", name);
        recipe.setId((int)recipe_id);
        recipe.setName(name);
        recipe.setProcedure(procedure);
        recipe.setServing(serving);

        Log.v("EditRecipeServing", String.valueOf(serving));

        db.updateRecipe(recipe);
        db.close();
        finish();
        /*
        Recipe recipe1 = new Recipe(name,procedure,serving,1);
        long recipe_id = db.createRecipe(recipe1);
        Log.d("Get Recipe", "Get All Recipe");
        List<Recipe> allRecipe = db.getAllRecipe();
        for (Recipe recipe : allRecipe){
            Log.d("Recipe Content", recipe.toString());
        }
        */

        return true;
    }
}
