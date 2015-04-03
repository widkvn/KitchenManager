package com.example.kevinwidjaja.kitchenmanager;

import android.test.AndroidTestCase;
import android.util.Log;

import java.util.List;

/** Recipe Table Unit test
 * Created by kevinwidjaja on 4/2/15.
 */
public class RecipeTest extends AndroidTestCase {

    //Database helper
    private DBHelper db;

    public void testRecipe(){
        Log.d("Testing Recipe", "Start Testing Recipe");
        db = new DBHelper(mContext);

        //creating Recipe
        Recipe recipe1 = new Recipe("Rice","steam",1,1);
        Recipe recipe2 = new Recipe("FriedChicken","fry",2,2);
        Recipe recipe3 = new Recipe("GrilledFish","grill",3,3);

        //inserting Recipe to database
        long recipe1_id = db.createRecipe(recipe1);
        long recipe2_id = db.createRecipe(recipe2);
        long recipe3_id = db.createRecipe(recipe3);

        Log.d("Recipe Count", "Recipe Count: " + db.getAllRecipe().size());

        //getting all Recipe content
        Log.d("Get Recipe", "Get All Recipe");
        List<Recipe> allRecipe = db.getAllRecipe();
        for (Recipe recipe : allRecipe){
            Log.d("Recipe Content", recipe.toString());
        }

        Log.d("Get Recipe", "Get each Recipe");
        Log.d("id 1 metric", db.getRecipe(1).toString());
        Log.d("id 2 metric", db.getRecipe(2).toString());
        Log.d("id 3 metric", db.getRecipe(3).toString());

        Log.d("Update Recipe", "Update id 3");
        recipe3.setId((int)recipe3_id);
        recipe3.setName("SpringRolls");
        recipe3.setProcedure("frytwotimes");
        Log.d("Recipe 3 var", recipe3.toString());
        db.updateRecipe(recipe3);
        Log.d("Updated id 3", db.getRecipe(3).toString());

        Log.d("Delete Recipe", "Delete All Recipe");
        //deleting Recipe
        db.deleteRecipe(recipe1_id);
        db.deleteRecipe(recipe2_id);
        db.deleteRecipe(recipe3_id);

        Log.d("Recipe Count", "Recipe Count after delete: " + db.getAllRecipe().size());

        //close database
        db.close();
        Log.d("Testing Recipe", "End of Recipe test");
    }

}
