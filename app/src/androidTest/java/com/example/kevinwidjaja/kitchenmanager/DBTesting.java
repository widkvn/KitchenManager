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
        Log.d("Testing Inventory", "Start Testing Inventory");
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
        Log.d("Testing Inventory", "End of Inventory test");
    }

    public void testUnitMeasure(){
        Log.d("Testing UnitMeasure", "Start Testing UnitMeasure");
        db = new DBHelper(mContext);

        //creating unitMeasure
        UnitMeasure unitMeasure1 = new UnitMeasure("cm");
        UnitMeasure unitMeasure2 = new UnitMeasure("gallon");
        UnitMeasure unitMeasure3 = new UnitMeasure("piece");

        //inserting unitMeasure to database
        long unitMeasure1_id = db.createUnitMeasure(unitMeasure1);
        long unitMeasure2_id = db.createUnitMeasure(unitMeasure2);
        long unitMeasure3_id = db.createUnitMeasure(unitMeasure3);

        Log.d("UnitMeasure Count", "UnitMeasure Count: " + db.getAllUnitMeasure().size());

        //getting all inventory name
        Log.d("Get UnitMeasure", "Get All UnitMeasure");
        List<UnitMeasure> allUnitMeasure = db.getAllUnitMeasure();
        for (UnitMeasure unitMeasure : allUnitMeasure){
            Log.d("UnitMeasure Metric", unitMeasure.getMetric());
        }

        Log.d("Get UnitMeasures", "Get each UnitMeasure");
        Log.d("id 1 metric", db.getUnitMeasure(1).getMetric());
        Log.d("id 2 metric", db.getUnitMeasure(2).getMetric());
        Log.d("id 3 metric", db.getUnitMeasure(3).getMetric());

        Log.d("Update UnitMeasure", "Update id 3");
        unitMeasure3.setId((int)unitMeasure3_id);
        unitMeasure3.setMetric("liter");
        Log.d("UnitMeasure 3 var", unitMeasure3.toString());
        db.updateUnitMeasure(unitMeasure3);
        Log.d("Updated id 3", db.getUnitMeasure(3).toString());

        Log.d("Delete UnitMeasures", "Delete All UnitMeasure");
        //deleting inventory
        db.deleteUnitMeasure(unitMeasure1_id);
        db.deleteUnitMeasure(unitMeasure2_id);
        db.deleteUnitMeasure(unitMeasure3_id);

        Log.d("UnitMeasure Count", "UnitMeasure Count after delete: " + db.getAllUnitMeasure().size());

        //close database
        db.close();
        Log.d("Testing UnitMeasure", "End of UnitMeasure test");
    }

    public void testErrorMessage(){
        Log.d("Testing ErrorMessage", "Start Testing ErrorMessage");
        db = new DBHelper(mContext);

        //creating errorMessage
        ErrorMessage errorMessage1 = new ErrorMessage("no such item");
        ErrorMessage errorMessage2 = new ErrorMessage("nothing updated");
        ErrorMessage errorMessage3 = new ErrorMessage("stop working");

        //inserting errorMessage to database
        long errorMessage1_id = db.createErrorMessage(errorMessage1);
        long errorMessage2_id = db.createErrorMessage(errorMessage2);
        long errorMessage3_id = db.createErrorMessage(errorMessage3);

        Log.d("ErrorMessage Count", "ErrorMessage Count: " + db.getAllErrorMessage().size());

        //getting all ErrorMessage message
        Log.d("Get ErrorMessage", "Get All ErrorMessage");
        List<ErrorMessage> allErrorMessage = db.getAllErrorMessage();
        for (ErrorMessage errorMessage : allErrorMessage){
            Log.d("ErrorMessage Message", errorMessage.getMsg());
        }

        Log.d("Get ErrorMessage", "Get each ErrorMessage");
        Log.d("id 1 metric", db.getErrorMessage(1).getMsg());
        Log.d("id 2 metric", db.getErrorMessage(2).getMsg());
        Log.d("id 3 metric", db.getErrorMessage(3).getMsg());

        Log.d("Update ErrorMessage", "Update id 3");
        errorMessage3.setId((int)errorMessage3_id);
        errorMessage3.setMsg("Hello!, I'm changed");
        Log.d("ErrorMessage 3 var", errorMessage3.toString());
        db.updateErrorMessage(errorMessage3);
        Log.d("Updated id 3", db.getErrorMessage(3).toString());

        Log.d("Delete ErrorMessage", "Delete All ErrorMessage");
        //deleting inventory
        db.deleteErrorMessage(errorMessage1_id);
        db.deleteErrorMessage(errorMessage2_id);
        db.deleteErrorMessage(errorMessage3_id);

        Log.d("ErrorMessage Count", "ErrorMessage Count after delete: " + db.getAllErrorMessage().size());

        //close database
        db.close();
        Log.d("Testing ErrorMessage", "End of ErrorMessage test");
    }

}
