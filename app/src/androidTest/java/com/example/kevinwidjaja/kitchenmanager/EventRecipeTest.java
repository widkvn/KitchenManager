package com.example.kevinwidjaja.kitchenmanager;

import android.test.AndroidTestCase;
import android.util.Log;

import java.util.List;

/** Event Recipe Table Unit test
 * Created by kevinwidjaja on 4/2/15.
 */
public class EventRecipeTest extends AndroidTestCase{

    //Database helper
    private DBHelper db;

    public void testEventRecipe(){
        Log.d("Testing EventRecipe", "Start Testing EventRecipe");
        db = new DBHelper(mContext);

        //creating eventRecipe
        EventRecipe eventRecipe1 = new EventRecipe(0,1);
        EventRecipe eventRecipe2 = new EventRecipe(2,3);
        EventRecipe eventRecipe3 = new EventRecipe(4,5);

        //inserting EventRecipe to database
        long eventRecipe1_id = db.createEventRecipe(eventRecipe1);
        long eventRecipe2_id = db.createEventRecipe(eventRecipe2);
        long eventRecipe3_id = db.createEventRecipe(eventRecipe3);

        Log.d("EventRecipe Count", "EventRecipe Count: " + db.getAllEventRecipe().size());

        //getting all EventRecipe content
        Log.d("Get EventRecipe", "Get All EventRecipe");
        List<EventRecipe> allEventRecipe = db.getAllEventRecipe();
        for (EventRecipe eventRecipe : allEventRecipe){
            Log.d("EventRecipe Content", eventRecipe.toString());
        }

        Log.d("Get EventRecipe", "Get each EventRecipe");
        Log.d("id 1 metric", db.getEventRecipe(1).toString());
        Log.d("id 2 metric", db.getEventRecipe(2).toString());
        Log.d("id 3 metric", db.getEventRecipe(3).toString());

        Log.d("Update EventRecipe", "Update id 3");
        eventRecipe3.setId((int)eventRecipe3_id);
        eventRecipe3.setEvent_id(6);
        eventRecipe3.setRecipe_id(7);
        Log.d("EventRecipe 3 var", eventRecipe3.toString());
        db.updateEventRecipe(eventRecipe3);
        Log.d("Updated id 3", db.getEventRecipe(3).toString());

        Log.d("Delete EventRecipe", "Delete All EventRecipe");
        //deleting EventRecipe
        db.deleteEventRecipe(eventRecipe1_id);
        db.deleteEventRecipe(eventRecipe2_id);
        db.deleteEventRecipe(eventRecipe3_id);

        Log.d("EventRecipe Count", "EventRecipe Count after delete: " + db.getAllEventRecipe().size());

        //close database
        db.close();
        Log.d("Testing EventRecipe", "End of EventRecipe test");
    }

}
