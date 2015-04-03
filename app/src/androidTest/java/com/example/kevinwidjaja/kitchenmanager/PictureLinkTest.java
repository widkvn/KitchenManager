package com.example.kevinwidjaja.kitchenmanager;

import android.test.AndroidTestCase;
import android.util.Log;

import java.util.List;

/**Picture Link table Unit test
 * Created by kevinwidjaja on 4/2/15.
 */
public class PictureLinkTest extends AndroidTestCase{

    //Database helper
    private DBHelper db;

    public void testPictureLink(){
        Log.d("Testing PictureLink", "Start Testing PictureLink");
        db = new DBHelper(mContext);

        //creating pictureLink
        PictureLink pictureLink1 = new PictureLink("blabla1.com");
        PictureLink pictureLink2 = new PictureLink("blabla2.com");
        PictureLink pictureLink3 = new PictureLink("blabla3.com");

        //inserting pictureLink to database
        long pictureLink1_id = db.createPictureLink(pictureLink1);
        long pictureLink2_id = db.createPictureLink(pictureLink2);
        long pictureLink3_id = db.createPictureLink(pictureLink3);

        Log.d("PictureLink Count", "PictureLink Count: " + db.getAllPictureLink().size());

        //getting all inventory name
        Log.d("Get PictureLink", "Get All PictureLink");
        List<PictureLink> allPictureLink = db.getAllPictureLink();
        for (PictureLink pictureLink : allPictureLink){
            Log.d("PictureLink Link", pictureLink.getLink());
        }

        Log.d("Get PictureLink", "Get each PictureLink");
        Log.d("id 1 metric", db.getPictureLink(1).getLink());
        Log.d("id 2 metric", db.getPictureLink(2).getLink());
        Log.d("id 3 metric", db.getPictureLink(3).getLink());

        Log.d("Update PictureLink", "Update id 3");
        pictureLink3.setId((int)pictureLink3_id);
        pictureLink3.setLink("changed.com");
        Log.d("PictureLink 3 var", pictureLink3.toString());
        db.updatePictureLink(pictureLink3);
        Log.d("Updated id 3", db.getPictureLink(3).toString());

        Log.d("Delete PictureLink", "Delete All PictureLink");
        //deleting inventory
        db.deletePictureLink(pictureLink1_id);
        db.deletePictureLink(pictureLink2_id);
        db.deletePictureLink(pictureLink3_id);

        Log.d("PictureLink Count", "PictureLink Count after delete: " + db.getAllUnitMeasure().size());

        //close database
        db.close();
        Log.d("Testing PictureLink", "End of PictureLink test");
    }

}
