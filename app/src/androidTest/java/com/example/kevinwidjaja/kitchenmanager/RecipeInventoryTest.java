package com.example.kevinwidjaja.kitchenmanager;

import android.test.AndroidTestCase;
import android.util.Log;

import java.util.List;

/** Recipe Inventory Table Unit Test
 * Created by kevinwidjaja on 4/2/15.
 */
public class RecipeInventoryTest extends AndroidTestCase{

    //Database helper
    private DBHelper db;

    public void testRecipeInventory(){
        Log.d("Testing RecipeInventory", "Start Testing RecipeInventory");
        db = new DBHelper(mContext);

        //creating RecipeInventory
        RecipeInventory recipeInventory1 = new RecipeInventory(1,1,1,1);
        RecipeInventory recipeInventory2 = new RecipeInventory(2,2,2,2);
        RecipeInventory recipeInventory3 = new RecipeInventory(3,3,3,3);

        //inserting RecipeInventory to database
        long recipeInventory1_id = db.createRecipeInventory(recipeInventory1);
        long recipeInventory2_id = db.createRecipeInventory(recipeInventory2);
        long recipeInventory3_id = db.createRecipeInventory(recipeInventory3);

        Log.d("RecipeInventory Count", "RecipeInventory Count: " + db.getAllRecipeInventory().size());

        //getting all RecipeInventory content
        Log.d("Get RecipeInventory", "Get All RecipeInventory");
        List<RecipeInventory> allRecipeInventory = db.getAllRecipeInventory();
        for (RecipeInventory recipeInventory : allRecipeInventory){
            Log.d("RecipeInventory Content", recipeInventory.toString());
        }

        Log.d("Get RecipeInventory", "Get each RecipeInventory");
        Log.d("id 1 metric", db.getRecipeInventory(1).toString());
        Log.d("id 2 metric", db.getRecipeInventory(2).toString());
        Log.d("id 3 metric", db.getRecipeInventory(3).toString());

        Log.d("Update RecipeInventory", "Update id 3");
        recipeInventory3.setId((int)recipeInventory3_id);
        recipeInventory3.setRecipe_id(4);
        recipeInventory3.setInventory_id(4);
        recipeInventory3.setQuantity(4);
        Log.d("Recipe 3 var", recipeInventory3.toString());
        db.updateRecipeInventory(recipeInventory3);
        Log.d("Updated id 3", db.getRecipeInventory(3).toString());

        Log.d("Delete RecipeInventory", "Delete All RecipeInventory");
        //deleting RecipeInventory
        db.deleteRecipeInventory(recipeInventory1_id);
        db.deleteRecipeInventory(recipeInventory2_id);
        db.deleteRecipeInventory(recipeInventory3_id);

        Log.d("RecipeInventory Count", "RecipeInventory Count after delete: " + db.getAllRecipeInventory().size());

        //close database
        db.close();
        Log.d("Testing RecipeInventory", "End of RecipeInventory test");
    }

}
