package com.example.kevinwidjaja.kitchenmanager;

import android.test.AndroidTestCase;
import android.util.Log;

import java.util.List;

/** ErrorMessage Table Unit test
 * Created by kevinwidjaja on 4/2/15.
 */
public class ErrorMessageTest extends AndroidTestCase {

    //Database helper
    private DBHelper db;

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
