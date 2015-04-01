package com.example.kevinwidjaja.kitchenmanager;

import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.util.Log;

import java.util.List;

/** Database Unit Test
 * Created by kevinwidjaja on 3/31/15.
 */
public class DBTesting extends AndroidTestCase{

    //Database helper
    private DBHelper db;

    public void testInventory(){
        db = new DBHelper(mContext);

        //creating inventory
        Inventory inv1 = new Inventory("apple",0,5);
        Inventory inv2 = new Inventory("banana",0,3);
        Inventory inv3 = new Inventory("orange",0,2);

        //inserting inventory to database
        long inv1_id = db.createInventory(inv1);
        long inv2_id = db.createInventory(inv2);
        long inv3_id = db.createInventory(inv3);

        Log.d("Inventory Count", "Inventory Count: " + db.getAllInventories().size());

        //getting all inventory name
        Log.d("Get Inventories", "Get All Inventory");
        List<Inventory> allInventory = db.getAllInventories();
        for (Inventory inventory : allInventory){
            Log.d("Inventory Name", inventory.getName());
        }

        Log.d("Get Inventories", "Get each Inventory");
        Log.d("id 1 name", db.getInventory(1).getName());
        Log.d("id 2 name", db.getInventory(2).getName());
        Log.d("id 3 name", db.getInventory(3).getName());

        Log.d("Update Inventory", "Update id 3");
        inv3.setId((int)inv3_id);
        inv3.setName("Melon");
        inv3.setUnit_id(1);
        inv3.setQuantity(10);
        Log.d("Inventory 3 var", inv3.toString());
        db.updateInventory(inv3);
        Log.d("Updated id 3", db.getInventory(3).toString());

        Log.d("Delete Inventories", "Delete All Inventory");
        //deleting inventory
        db.deleteInventory(inv1_id);
        db.deleteInventory(inv2_id);
        db.deleteInventory(inv3_id);

        Log.d("Inventory Count", "Inventory Count after delete: " + db.getAllInventories().size());

        //close database
        db.close();
    }
}
