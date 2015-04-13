package com.example.kevinwidjaja.kitchenmanager;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;


public class RecipeActivityAddRecipe extends ActionBarActivity {

    Button addRecipeButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_activity_add_recipe);

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
                finish();
            }
        });

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
        DBHelper db;
        String name="", procedure="";
        int serving=0;
        db = new DBHelper(this);
        recipeName=(EditText)findViewById(R.id.recipeName);
        procedureField=(EditText)findViewById(R.id.procedure);
        name=recipeName.getText().toString();
        procedure=procedureField.getText().toString();
        Log.v("RecipeAddRecipeForm", name);

        Recipe recipe1 = new Recipe(name,procedure,serving,1);
        long recipe_id = db.createRecipe(recipe1);
        Log.d("Get Recipe", "Get All Recipe");
        List<Recipe> allRecipe = db.getAllRecipe();
        for (Recipe recipe : allRecipe){
            Log.d("Recipe Content", recipe.toString());
        }


        return true;
    }

}
