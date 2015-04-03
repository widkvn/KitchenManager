package com.example.kevinwidjaja.kitchenmanager;

import android.test.AndroidTestCase;
import android.util.Log;

import java.util.List;

/** UnitMeasure Table Unit test
 * Created by kevinwidjaja on 4/2/15.
 */
public class UnitMeasureTest extends AndroidTestCase{

    //Database helper
    private DBHelper db;

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
        //deleting unitMeasure
        db.deleteUnitMeasure(unitMeasure1_id);
        db.deleteUnitMeasure(unitMeasure2_id);
        db.deleteUnitMeasure(unitMeasure3_id);

        Log.d("UnitMeasure Count", "UnitMeasure Count after delete: " + db.getAllUnitMeasure().size());

        //close database
        db.close();
        Log.d("Testing UnitMeasure", "End of UnitMeasure test");
    }

}
