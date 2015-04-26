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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class RecipeActivityViewRecipe extends ActionBarActivity {

    private Toolbar toolbar;
    private Toolbar toolbar_bottom;

    Recipe recipe;
    DBHelper db;
    int recipe_id;

    TextView text;
    List<Inventory> allInventory;
    List<RecipeInventory> allRecipeInventory;
    List<String> InventoryNameList;
    List<Integer> InventoryQtyList;
    List<Integer> InventoryUnitList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_activity_view_recipe);

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
                        intent = new Intent(RecipeActivityViewRecipe.this, InventoryActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.shoppinglist:
                        Toast.makeText(getApplicationContext(), "Shopping List", Toast.LENGTH_SHORT).show();
                        intent = new Intent(RecipeActivityViewRecipe.this, ShoppingListActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.recipe:
                        Toast.makeText(getApplicationContext(), "Recipe", Toast.LENGTH_SHORT).show();
                        intent = new Intent(RecipeActivityViewRecipe.this, RecipeActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.event:
                        Toast.makeText(getApplicationContext(), "Event", Toast.LENGTH_SHORT).show();
                        intent = new Intent(RecipeActivityViewRecipe.this, EventActivity.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });
        toolbar_bottom.inflateMenu(R.menu.menu_bottomnav);

        Bundle extras = getIntent().getExtras();
        final String item_id=extras.getString("Item Id");
        recipe_id=Integer.parseInt(item_id);
        //Toast.makeText(getApplicationContext(),db.getRecipe(recipe_id).getName()+" "+db.getRecipe(recipe_id).getProcedure() , Toast.LENGTH_LONG).show();
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
                db=new DBHelper(RecipeActivityViewRecipe.this);
                db.deleteRecipe(recipe_id);
                db.close();
                finish();

            }
        });

        //db.close();
        //Text View
        setTextView();

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
    public void setTextView()
    {
        db=new DBHelper(RecipeActivityViewRecipe.this);
        InventoryNameList=new ArrayList<String>();
        InventoryQtyList=new ArrayList<Integer>();
        InventoryUnitList=new ArrayList<Integer>();

        Log.v("Viewrecipe","1st get all");
        allInventory=db.getAllInventories();
        Log.v("Viewrecipe","2nd get all");
        allRecipeInventory=db.getAllRecipeInventory();
        Log.v("Viewrecipe","after get all");

        String textview_list="Ingredients\n\n";

        for (RecipeInventory recipeInventory : allRecipeInventory)
        {
            if(recipeInventory.getRecipe_id()==recipe_id)
            {
                //Get inventory id from  RecipeInventory that corresponds to this recipe's id
                //Go through allInventory and wherever you find the inventory id, return the inventory name.
                InventoryQtyList.add(recipeInventory.getQuantity());
                for (Inventory inventory : allInventory)
                {
                    if(inventory.getId()==recipeInventory.getInventory_id())
                    {
                        InventoryNameList.add(inventory.getName());
                        InventoryUnitList.add(inventory.getUnit_id());
                        textview_list=textview_list+inventory.getName()+"\t"+recipeInventory.getQuantity()+"\t"+inventory.getUnit_id()+"\n";
                    }
                }

            }

        }

        String procedure="\nProcedure\n\n"+db.getRecipe(recipe_id).getProcedure();
        text=(TextView) findViewById(R.id.textView);
        String content=textview_list+procedure;
        text.setText(content);
        db.close();
    }
}
