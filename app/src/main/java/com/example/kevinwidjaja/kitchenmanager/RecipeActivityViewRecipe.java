package com.example.kevinwidjaja.kitchenmanager;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;


public class RecipeActivityViewRecipe extends ActionBarActivity {

    Recipe recipe;
    DBHelper db=new DBHelper(this);
    int recipe_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_activity_view_recipe);

        Bundle extras = getIntent().getExtras();
        final String item_id=extras.getString("Item Id");
        recipe_id=Integer.parseInt(item_id);
        Toast.makeText(getApplicationContext(),db.getRecipe(recipe_id).getName()+" "+db.getRecipe(recipe_id).getProcedure() , Toast.LENGTH_LONG).show();
        //Toast.makeText(getApplicationContext(), String.valueOf(recipe_id), Toast.LENGTH_LONG).show();


        //Edit Recipe button
        ImageButton recipe_button = (ImageButton) findViewById(R.id.editRecipeButton);
        recipe_button.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                // Perform action on click
                Intent myIntent = new Intent(RecipeActivityViewRecipe.this, RecipeActivityEditRecipe.class);
                myIntent.putExtra("Recipe Id",item_id);
                RecipeActivityViewRecipe.this.startActivity(myIntent);

            }
        });

        //Delete Recipe button
        ImageButton delete_recipe_button = (ImageButton) findViewById(R.id.deleteRecipeButton);
        delete_recipe_button.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                // Perform action on click
                db.deleteRecipe(recipe_id);
                finish();

            }
        });



}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recipe_activity_view_recipe, menu);
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
}
