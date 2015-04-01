package com.example.kevinwidjaja.kitchenmanager;

import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.util.Log;

/** Database Unit Test
 * Created by kevinwidjaja on 3/31/15.
 */
public class DBTesting extends AndroidTestCase{

    //Database helper
    DBHelper db;

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
    }
}
