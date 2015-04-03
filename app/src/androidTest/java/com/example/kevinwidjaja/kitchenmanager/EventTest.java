package com.example.kevinwidjaja.kitchenmanager;

import android.test.AndroidTestCase;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**Event Table Unit test
 * Created by kevinwidjaja on 4/2/15.
 */
public class EventTest extends AndroidTestCase{

    //Database helper
    private DBHelper db;

    public void testEvent(){
        Log.d("Testing Event", "Start Testing Event");
        db = new DBHelper(mContext);

        //creating event
        Event event1 = new Event("event1",new Date(),1,1);
        Event event2 = new Event("event2",new Date(),2,2);
        Event event3 = new Event("event3",new Date(),3,3);

        //inserting Event to database
        long event1_id = db.createEvent(event1);
        long event2_id = db.createEvent(event2);
        long event3_id = db.createEvent(event3);

        Log.d("Event Count", "Event Count: " + db.getAllEvent().size());

        //getting all Event content
        Log.d("Get Event", "Get All Event");
        List<Event> allEvent = db.getAllEvent();
        for (Event event : allEvent){
            Log.d("Event Content", event.toString());
        }

        Log.d("Get Event", "Get each Event");
        Log.d("id 1 metric", db.getEvent(1).toString());
        Log.d("id 2 metric", db.getEvent(2).toString());
        Log.d("id 3 metric", db.getEvent(3).toString());

        Log.d("Update Event", "Update id 3");
        event3.setId((int)event3_id);
        event3.setTitle("event4");
        event3.setDate(new Date());
        event3.setServing(4);
        event3.setPicture_id(4);
        Log.d("Event 3 var", event3.toString());
        db.updateEvent(event3);
        Log.d("Updated id 3", db.getEvent(3).toString());

        Log.d("Delete Event", "Delete All Event");
        //deleting Event
        db.deleteEvent(event1_id);
        db.deleteEvent(event2_id);
        db.deleteEvent(event3_id);

        Log.d("Event Count", "Event Count after delete: " + db.getAllEvent().size());

        //close database
        db.close();
        Log.d("Testing Event", "End of Event test");
    }


}
