package com.example.kevinwidjaja.kitchenmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/** Database Helper class for initialization
 * Created by kevinwidjaja on 3/30/15.
 */
public class DBHelper extends SQLiteOpenHelper{

    //Logcat tag
    private static final String LOG = "DBHelper";

    //Database version
    private static final int DATABASE_VERSION = 1;

    //Database name
    private static final String DATABASE_NAME = "kitchenManager";

    //Table name
    private static final String TABLE_ERRORMESSAGE = "errorMessages";
    private static final String TABLE_EVENT = "events";
    private static final String TABLE_EVENT_RECIPE = "eventRecipes";
    private static final String TABLE_INVENTORY = "inventories";
    private static final String TABLE_PICTURELINK = "picturelinks";
    private static final String TABLE_RECIPE = "recipes";
    private static final String TABLE_RECIPEINVETORY = "recipeInventories";
    private static final String TABLE_UNITMEASURE = "unitMeasures";

    //Common column name
    private static final String KEY_ID = "id";
    private static final String KEY_QUANTITY = "quantity";
    private static final String KEY_SERVING = "serving";
    private static final String KEY_PICTURE_ID = "picture_id";
    private static final String KEY_RECIPE_ID = "recipe_id";
    private static final String KEY_NAME = "name";

    // ------------------- ErrorMessage table - column names
    //id column - defined common
    private static final String KEY_MESSAGE = "error_message";

    // ------------------- Event table - column names
    //id column - defined common
    private static final String KEY_TITLE = "title";
    private static final String KEY_DATE = "date";
    //serving column - defined common
    //picture_id column - defined common

    // ------------------- Event Recipe table - column names
    //id column - defined common
    private static final String KEY_EVENT_ID = "event_id";
    //recipe_id column - defined common

    // ------------------- Inventory table - column names
    //id column - defined common
    //name column - defined common
    private static final String KEY_UNIT_ID = "unit_id";
    //quantity column - defined common

    // ------------------- Picture link table - column names
    //id column - defined common
    private static final String KEY_LINK = "picture_link";

    // ------------------- Recipe table - column names
    //id column - defined common
    //name column - defined common
    private static final String KEY_PROCEDURE = "procedure";
    //serving column - defined common
    //picture_id column - defined common

    // ------------------- Recipe Inventory table - column names
    //recipe_id column - defined common
    private static final String KEY_INVENTORY_ID = "inventory_id";
    //quantity column - defined common

    // ------------------- Unit Measure table - column names
    //id column - defined common
    private static final String KEY_METRIC = "metric";

    //Table create statements
    //ErrorMessage table create statement
    private static final String CREATE_TABLE_ERRORMESSAGE = "CREATE TABLE"
            + TABLE_ERRORMESSAGE
            + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_MESSAGE + " TEXT"
            + ")";

    //Event table create statement
    private static final String CREATE_TABLE_EVENT = "CREATE TABLE"
            + TABLE_EVENT
            + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_TITLE + " TEXT,"
            + KEY_DATE + " DATETIME,"
            + KEY_SERVING + " INTEGER,"
            + KEY_PICTURE_ID + " INTEGER"
            + ")";

    //Event Recipe table create statement
    private static final String CREATE_TABLE_EVENTRECIPE = "CREATE TABLE"
            + TABLE_EVENT_RECIPE
            + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_EVENT_ID + " INTEGER,"
            + KEY_RECIPE_ID + " INTEGER"
            + ")";

    //Inventory table create statement
    private static final String CREATE_TABLE_INVENTORY = "CREATE TABLE"
            + TABLE_INVENTORY
            + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_NAME + " TEXT,"
            + KEY_UNIT_ID + " INTEGER,"
            + KEY_QUANTITY + " INTEGER"
            + ")";

    //Picture link table create statement
    private static final String CREATE_TABLE_PICTURELINK = "CREATE TABLE"
            + TABLE_PICTURELINK
            + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_LINK + " TEXT"
            + ")";

    //Recipe table create statement
    private static final String CREATE_TABLE_RECIPE = "CREATE TABLE"
            + TABLE_RECIPE
            + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_NAME + " TEXT,"
            + KEY_PROCEDURE + " TEXT,"
            + KEY_SERVING + " INTEGER,"
            + KEY_PICTURE_ID + " INTEGER"
            + ")";

    //Recipe Inventory table create statement
    private static final String CREATE_TABLE_RECIPEINVENTORY = "CREATE TABLE"
            + TABLE_RECIPEINVETORY
            + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_RECIPE_ID + " INTEGER,"
            + KEY_INVENTORY_ID + " INTEGER,"
            + KEY_QUANTITY + " INTEGER"
            + ")";

    //Unit measure create statement
    private static final String CREATE_TABLE_UNITMEASURE = "CREATE TABLE"
            + TABLE_UNITMEASURE
            + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_NAME + " TEXT"
            + ")";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //creating required database
        db.execSQL(CREATE_TABLE_ERRORMESSAGE);
        db.execSQL(CREATE_TABLE_EVENT);
        db.execSQL(CREATE_TABLE_EVENTRECIPE);
        db.execSQL(CREATE_TABLE_INVENTORY);
        db.execSQL(CREATE_TABLE_PICTURELINK);
        db.execSQL(CREATE_TABLE_RECIPE);
        db.execSQL(CREATE_TABLE_RECIPEINVENTORY);
        db.execSQL(CREATE_TABLE_UNITMEASURE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //on upgrade drop older tables
        db.execSQL("DROP TABLE IF IT EXISTS " + TABLE_ERRORMESSAGE);
        db.execSQL("DROP TABLE IF IT EXISTS " + TABLE_EVENT);
        db.execSQL("DROP TABLE IF IT EXISTS " + TABLE_EVENT_RECIPE);
        db.execSQL("DROP TABLE IF IT EXISTS " + TABLE_INVENTORY);
        db.execSQL("DROP TABLE IF IT EXISTS " + TABLE_PICTURELINK);
        db.execSQL("DROP TABLE IF IT EXISTS " + TABLE_RECIPE);
        db.execSQL("DROP TABLE IF IT EXISTS " + TABLE_RECIPEINVETORY);
        db.execSQL("DROP TABLE IF IT EXISTS " + TABLE_UNITMEASURE);

        //create new tables
        onCreate(db);
    }

    /**
     * Closing database
     */
    public void closeDB(){
        SQLiteDatabase db = this.getReadableDatabase();
        if(db != null && db.isOpen())
            db.close();
    }

    // ------------------- Inventory table methods
    /**
     * creating an inventory
     */
    public long createInventory(Inventory inventory){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, inventory.getId());
        values.put(KEY_NAME, inventory.getName());
        values.put(KEY_UNIT_ID, inventory.getUnit_id());
        values.put(KEY_QUANTITY, inventory.getQuantity());

        //insert row
        long inventory_id = db.insert(TABLE_INVENTORY, null, values);
        return inventory_id;
    }

    /**
     * Fetching an inventory
     */
    public Inventory getInventory(long inventory_id){
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM "
                + TABLE_INVENTORY + " WHERE "
                + KEY_ID + " = "
                + inventory_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if(c != null)
            c.moveToFirst();

        Inventory inventory = new Inventory();
        inventory.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        inventory.setName(c.getString(c.getColumnIndex(KEY_NAME)));
        inventory.setQuantity(c.getInt(c.getColumnIndex(KEY_QUANTITY)));
        inventory.setUnit_id(c.getInt(c.getColumnIndex(KEY_UNIT_ID)));

        return inventory;
    }

    /**
     * Fetching all inventory
     */
    public List<Inventory> getAllInventories(){
        List<Inventory> inventories = new ArrayList<Inventory>();
        String selectQuery = "SELECT * FROM "
                + TABLE_INVENTORY;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if(c.moveToFirst()) {
            do {
                Inventory inventory = new Inventory();
                inventory.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                inventory.setName(c.getString(c.getColumnIndex(KEY_NAME)));
                inventory.setQuantity(c.getInt(c.getColumnIndex(KEY_QUANTITY)));
                inventory.setUnit_id(c.getInt(c.getColumnIndex(KEY_UNIT_ID)));

                // adding to inventory list
                inventories.add(inventory);
            } while(c.moveToNext());
        }
        return inventories;
    }

    /**
     * Delete an inventory
     */
    public void deleteInventory(long inventory_id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_INVENTORY,
                KEY_ID + " = ?",
                new String[] {String.valueOf(inventory_id)});
    }
}
