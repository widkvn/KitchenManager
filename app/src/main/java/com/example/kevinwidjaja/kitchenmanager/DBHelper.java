package com.example.kevinwidjaja.kitchenmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Picture;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
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
    private static final String TABLE_EVENTRECIPE = "eventRecipes";
    private static final String TABLE_INVENTORY = "inventories";
    private static final String TABLE_PICTURELINK = "pictureLinks";
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
    private static final String CREATE_TABLE_ERRORMESSAGE = "CREATE TABLE "
            + TABLE_ERRORMESSAGE
            + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_MESSAGE + " TEXT"
            + ")";

    //Event table create statement
    private static final String CREATE_TABLE_EVENT = "CREATE TABLE "
            + TABLE_EVENT
            + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_TITLE + " TEXT,"
            + KEY_DATE + " DATETIME,"
            + KEY_SERVING + " INTEGER,"
            + KEY_PICTURE_ID + " INTEGER"
            + ")";

    //Event Recipe table create statement
    private static final String CREATE_TABLE_EVENTRECIPE = "CREATE TABLE "
            + TABLE_EVENTRECIPE
            + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_EVENT_ID + " INTEGER,"
            + KEY_RECIPE_ID + " INTEGER"
            + ")";

    //Inventory table create statement
    private static final String CREATE_TABLE_INVENTORY = "CREATE TABLE "
            + TABLE_INVENTORY
            + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_NAME + " TEXT,"
            + KEY_UNIT_ID + " INTEGER,"
            + KEY_QUANTITY + " INTEGER"
            + ")";

    //Picture link table create statement
    private static final String CREATE_TABLE_PICTURELINK = "CREATE TABLE "
            + TABLE_PICTURELINK
            + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_LINK + " TEXT"
            + ")";

    //Recipe table create statement
    private static final String CREATE_TABLE_RECIPE = "CREATE TABLE "
            + TABLE_RECIPE
            + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_NAME + " TEXT,"
            + KEY_PROCEDURE + " TEXT,"
            + KEY_SERVING + " INTEGER,"
            + KEY_PICTURE_ID + " INTEGER"
            + ")";

    //Recipe Inventory table create statement
    private static final String CREATE_TABLE_RECIPEINVENTORY = "CREATE TABLE "
            + TABLE_RECIPEINVETORY
            + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_RECIPE_ID + " INTEGER,"
            + KEY_INVENTORY_ID + " INTEGER,"
            + KEY_QUANTITY + " INTEGER"
            + ")";

    //Unit measure create statement
    private static final String CREATE_TABLE_UNITMEASURE = "CREATE TABLE "
            + TABLE_UNITMEASURE
            + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_METRIC + " TEXT"
            + ")";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //creating required database
        db.execSQL(CREATE_TABLE_ERRORMESSAGE);
        //db.execSQL(CREATE_TABLE_EVENT);
        db.execSQL(CREATE_TABLE_EVENTRECIPE);
        db.execSQL(CREATE_TABLE_INVENTORY);
        db.execSQL(CREATE_TABLE_PICTURELINK);
        //db.execSQL(CREATE_TABLE_RECIPE);
        //db.execSQL(CREATE_TABLE_RECIPEINVENTORY);
        db.execSQL(CREATE_TABLE_UNITMEASURE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //on upgrade drop older tables
        db.execSQL("DROP TABLE IF IT EXISTS " + TABLE_ERRORMESSAGE);
        //db.execSQL("DROP TABLE IF IT EXISTS " + TABLE_EVENT);
        db.execSQL("DROP TABLE IF IT EXISTS " + TABLE_EVENTRECIPE);
        db.execSQL("DROP TABLE IF IT EXISTS " + TABLE_INVENTORY);
        db.execSQL("DROP TABLE IF IT EXISTS " + TABLE_PICTURELINK);
        //db.execSQL("DROP TABLE IF IT EXISTS " + TABLE_RECIPE);
        //db.execSQL("DROP TABLE IF IT EXISTS " + TABLE_RECIPEINVETORY);
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
        //values.put(KEY_ID, inventory.getId()); //can't do this since it's going to be autoincrement
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

                // adding to inventories
                inventories.add(inventory);
            } while(c.moveToNext());
        }
        return inventories;
    }

    /**
     * Update an inventory
     */
    public int updateInventory(Inventory inventory){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, inventory.getName());
        values.put(KEY_UNIT_ID, inventory.getUnit_id());
        values.put(KEY_QUANTITY, inventory.getQuantity());

        //updating row
        return db.update(TABLE_INVENTORY, values,
                KEY_ID + " = ?",
                new String[] {String.valueOf(inventory.getId())});

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

    // ------------------- Unit Measure table methods
    /**
     * creating an unitMeasure
     */
    public long createUnitMeasure(UnitMeasure unitMeasure){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_METRIC, unitMeasure.getMetric());

        //insert row
        long unitMeasure_id = db.insert(TABLE_UNITMEASURE, null, values);
        return unitMeasure_id;
    }

    /**
     * Fetching an unitMeasure
     */
    public UnitMeasure getUnitMeasure(long unitMeasure_id){
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM "
                + TABLE_UNITMEASURE + " WHERE "
                + KEY_ID + " = "
                + unitMeasure_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if(c != null)
            c.moveToFirst();

        UnitMeasure unitMeasure = new UnitMeasure();
        unitMeasure.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        unitMeasure.setMetric(c.getString(c.getColumnIndex(KEY_METRIC)));

        return unitMeasure;
    }

    /**
     * Fetching all unitMeasure
     */
    public List<UnitMeasure> getAllUnitMeasure(){
        List<UnitMeasure> unitMeasures = new ArrayList<UnitMeasure>();
        String selectQuery = "SELECT * FROM "
                + TABLE_UNITMEASURE;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if(c.moveToFirst()) {
            do {
                UnitMeasure unitMeasure = new UnitMeasure();
                unitMeasure.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                unitMeasure.setMetric(c.getString(c.getColumnIndex(KEY_METRIC)));

                // adding to unitMeasures
                unitMeasures.add(unitMeasure);
            } while(c.moveToNext());
        }
        return unitMeasures;
    }

    /**
     * Update an unitMeasure
     */
    public int updateUnitMeasure(UnitMeasure unitMeasure){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_METRIC, unitMeasure.getMetric());

        //updating row
        return db.update(TABLE_UNITMEASURE, values,
                KEY_ID + " = ?",
                new String[] {String.valueOf(unitMeasure.getId())});

    }

    /**
     * Delete an unitMeasure
     */
    public void deleteUnitMeasure(long unitMeasure_id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_UNITMEASURE,
                KEY_ID + " = ?",
                new String[] {String.valueOf(unitMeasure_id)});
    }

    // ------------------- Error Message table methods
    /**
     * creating an errorMessage
     */
    public long createErrorMessage(ErrorMessage errorMessage){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_MESSAGE, errorMessage.getMsg());

        //insert row
        long errorMessage_id = db.insert(TABLE_ERRORMESSAGE, null, values);
        return errorMessage_id;
    }

    /**
     * Fetching an errorMessage
     */
    public ErrorMessage getErrorMessage(long errorMessage_id){
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM "
                + TABLE_ERRORMESSAGE + " WHERE "
                + KEY_ID + " = "
                + errorMessage_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if(c != null)
            c.moveToFirst();

        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        errorMessage.setMsg(c.getString(c.getColumnIndex(KEY_MESSAGE)));

        return errorMessage;
    }

    /**
     * Fetching all errorMessage
     */
    public List<ErrorMessage> getAllErrorMessage(){
        List<ErrorMessage> errorMessages = new ArrayList<ErrorMessage>();
        String selectQuery = "SELECT * FROM "
                + TABLE_ERRORMESSAGE;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if(c.moveToFirst()) {
            do {
                ErrorMessage errorMessage = new ErrorMessage();
                errorMessage.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                errorMessage.setMsg(c.getString(c.getColumnIndex(KEY_MESSAGE)));

                // adding to errorMessages
                errorMessages.add(errorMessage);
            } while(c.moveToNext());
        }
        return errorMessages;
    }

    /**
     * Update an errorMessage
     */
    public int updateErrorMessage(ErrorMessage errorMessage){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_MESSAGE, errorMessage.getMsg());

        //updating row
        return db.update(TABLE_ERRORMESSAGE, values,
                KEY_ID + " = ?",
                new String[] {String.valueOf(errorMessage.getId())});

    }

    /**
     * Delete an errorMessage
     */
    public void deleteErrorMessage(long errorMessage_id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ERRORMESSAGE,
                KEY_ID + " = ?",
                new String[] {String.valueOf(errorMessage_id)});
    }

    // ------------------- Picture Link table methods
    /**
     * creating an pictureLink
     */
    public long createPictureLink(PictureLink pictureLink){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_LINK, pictureLink.getLink());

        //insert row
        long pictureLink_id = db.insert(TABLE_PICTURELINK, null, values);
        return pictureLink_id;
    }

    /**
     * Fetching an pictureLink
     */
    public PictureLink getPictureLink(long pictureLink_id){
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM "
                + TABLE_PICTURELINK + " WHERE "
                + KEY_ID + " = "
                + pictureLink_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if(c != null)
            c.moveToFirst();

        PictureLink pictureLink = new PictureLink();
        pictureLink.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        pictureLink.setLink(c.getString(c.getColumnIndex(KEY_LINK)));

        return pictureLink;
    }

    /**
     * Fetching all pictureLink
     */
    public List<PictureLink> getAllPictureLink(){
        List<PictureLink> pictureLinks = new ArrayList<PictureLink>();
        String selectQuery = "SELECT * FROM "
                + TABLE_PICTURELINK;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if(c.moveToFirst()) {
            do {
                PictureLink pictureLink = new PictureLink();
                pictureLink.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                pictureLink.setLink(c.getString(c.getColumnIndex(KEY_LINK)));

                // adding to pictureLinks
                pictureLinks.add(pictureLink);
            } while(c.moveToNext());
        }
        return pictureLinks;
    }

    /**
     * Update an pictureLink
     */
    public int updatePictureLink(PictureLink pictureLink){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_LINK, pictureLink.getLink());

        //updating row
        return db.update(TABLE_PICTURELINK, values,
                KEY_ID + " = ?",
                new String[] {String.valueOf(pictureLink.getId())});

    }

    /**
     * Delete an pictureLink
     */
    public void deletePictureLink(long pictureLink_id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PICTURELINK,
                KEY_ID + " = ?",
                new String[] {String.valueOf(pictureLink_id)});
    }

    // ------------------- Event Recipe table methods
    /**
     * creating an eventRecipe
     */
    public long createEventRecipe(EventRecipe eventRecipe){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_EVENT_ID, eventRecipe.getEvent_id());
        values.put(KEY_RECIPE_ID, eventRecipe.getRecipe_id());

        //insert row
        long eventRecipe_id = db.insert(TABLE_EVENTRECIPE, null, values);
        return eventRecipe_id;
    }

    /**
     * Fetching an eventRecipe
     */
    public EventRecipe getEventRecipe(long eventRecipe_id){
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM "
                + TABLE_EVENTRECIPE + " WHERE "
                + KEY_ID + " = "
                + eventRecipe_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if(c != null)
            c.moveToFirst();

        EventRecipe eventRecipe = new EventRecipe();
        eventRecipe.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        eventRecipe.setEvent_id(c.getInt(c.getColumnIndex(KEY_EVENT_ID)));
        eventRecipe.setRecipe_id(c.getInt(c.getColumnIndex(KEY_RECIPE_ID)));

        return eventRecipe;
    }

    /**
     * Fetching all eventRecipe
     */
    public List<EventRecipe> getAllEventRecipe(){
        List<EventRecipe> eventRecipes = new ArrayList<EventRecipe>();
        String selectQuery = "SELECT * FROM "
                + TABLE_EVENTRECIPE;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if(c.moveToFirst()) {
            do {
                EventRecipe eventRecipe = new EventRecipe();
                eventRecipe.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                eventRecipe.setEvent_id(c.getInt(c.getColumnIndex(KEY_EVENT_ID)));
                eventRecipe.setRecipe_id(c.getInt(c.getColumnIndex(KEY_RECIPE_ID)));

                // adding to eventRecipes
                eventRecipes.add(eventRecipe);
            } while(c.moveToNext());
        }
        return eventRecipes;
    }

    /**
     * Update an eventRecipe
     */
    public int updateEventRecipe(EventRecipe eventRecipe){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_EVENT_ID, eventRecipe.getEvent_id());
        values.put(KEY_RECIPE_ID, eventRecipe.getRecipe_id());

        //updating row
        return db.update(TABLE_EVENTRECIPE, values,
                KEY_ID + " = ?",
                new String[] {String.valueOf(eventRecipe.getId())});

    }

    /**
     * Delete an eventRecipe
     */
    public void deleteEventRecipe(long eventRecipe_id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EVENTRECIPE,
                KEY_ID + " = ?",
                new String[] {String.valueOf(eventRecipe_id)});
    }

    // ------------------- Event table methods
    /**
     * creating an event
     */
    public long createEvent(Event event){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, event.getTitle());
        values.put(KEY_DATE, event.getDate().toString());
        values.put(KEY_SERVING, event.getServing());
        values.put(KEY_PICTURE_ID, event.getPicture_id());

        //insert row
        long event_id = db.insert(TABLE_EVENT, null, values);
        return event_id;
    }

    /**
     * Fetching an event
     */
    public Event getEvent(long event_id){
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM "
                + TABLE_EVENT + " WHERE "
                + KEY_ID + " = "
                + event_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if(c != null)
            c.moveToFirst();

        Event event = new Event();
        event.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        event.setTitle(c.getString(c.getColumnIndex(KEY_TITLE)));
        event.setDate(new Date(c.getString(c.getColumnIndex(KEY_DATE))));
        event.setServing(c.getInt(c.getColumnIndex(KEY_SERVING)));
        event.setPicture_id(c.getInt(c.getColumnIndex(KEY_PICTURE_ID)));

        return event;
    }

    /**
     * Fetching all event
     */
    public List<Event> getAllEvent(){
        List<Event> events = new ArrayList<Event>();
        String selectQuery = "SELECT * FROM "
                + TABLE_EVENT;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if(c.moveToFirst()) {
            do {
                Event event = new Event();
                event.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                event.setTitle(c.getString(c.getColumnIndex(KEY_TITLE)));
                event.setDate(new Date(c.getString(c.getColumnIndex(KEY_DATE))));
                event.setServing(c.getInt(c.getColumnIndex(KEY_SERVING)));
                event.setPicture_id(c.getInt(c.getColumnIndex(KEY_PICTURE_ID)));

                // adding to events
                events.add(event);
            } while(c.moveToNext());
        }
        return events;
    }

    /**
     * Update an event
     */
    public int updateEvent(Event event){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, event.getTitle());
        values.put(KEY_DATE, event.getDate().toString());
        values.put(KEY_SERVING, event.getServing());
        values.put(KEY_PICTURE_ID, event.getPicture_id());

        //updating row
        return db.update(TABLE_EVENT, values,
                KEY_ID + " = ?",
                new String[] {String.valueOf(event.getId())});

    }

    /**
     * Delete an eventRecipe
     */
    public void deleteEvent(long event_id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EVENT,
                KEY_ID + " = ?",
                new String[] {String.valueOf(event_id)});
    }

    // ------------------- Recipe table methods
    /**
     * creating a recipe
     */
    public long createRecipe(Recipe recipe){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, recipe.getName());
        values.put(KEY_PROCEDURE, recipe.getProcedure());
        values.put(KEY_SERVING, recipe.getServing());
        values.put(KEY_PICTURE_ID, recipe.getPicture_id());

        //insert row
        long recipe_id = db.insert(TABLE_RECIPE, null, values);
        return recipe_id;
    }

    /**
     * Fetching a recipe
     */
    public Recipe getRecipe(long recipe_id){
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM "
                + TABLE_RECIPE + " WHERE "
                + KEY_ID + " = "
                + recipe_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if(c != null)
            c.moveToFirst();

        Recipe recipe = new Recipe();
        recipe.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        recipe.setName(c.getString(c.getColumnIndex(KEY_NAME)));
        recipe.setProcedure(c.getString(c.getColumnIndex(KEY_PROCEDURE)));
        recipe.setServing(c.getInt(c.getColumnIndex(KEY_SERVING)));
        recipe.setPicture_id(c.getInt(c.getColumnIndex(KEY_PICTURE_ID)));

        return recipe;
    }

    /**
     * Fetching all recipe
     */
    public List<Recipe> getAllRecipe(){
        List<Recipe> recipes = new ArrayList<Recipe>();
        String selectQuery = "SELECT * FROM "
                + TABLE_RECIPE;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if(c.moveToFirst()) {
            do {
                Recipe recipe = new Recipe();
                recipe.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                recipe.setName(c.getString(c.getColumnIndex(KEY_NAME)));
                recipe.setProcedure(c.getString(c.getColumnIndex(KEY_PROCEDURE)));
                recipe.setServing(c.getInt(c.getColumnIndex(KEY_SERVING)));
                recipe.setPicture_id(c.getInt(c.getColumnIndex(KEY_PICTURE_ID)));

                // adding to recipes
                recipes.add(recipe);
            } while(c.moveToNext());
        }
        return recipes;
    }

    /**
     * Update a recipe
     */
    public int updateRecipe(Recipe recipe){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, recipe.getName());
        values.put(KEY_PROCEDURE, recipe.getProcedure());
        values.put(KEY_SERVING, recipe.getServing());
        values.put(KEY_PICTURE_ID, recipe.getPicture_id());

        //updating row
        return db.update(TABLE_RECIPE, values,
                KEY_ID + " = ?",
                new String[] {String.valueOf(recipe.getId())});

    }

    /**
     * Delete a recipe
     */
    public void deleteRecipe(long recipe_id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RECIPE,
                KEY_ID + " = ?",
                new String[] {String.valueOf(recipe_id)});
    }

    // ------------------- Recipe Inventory table methods
    /**
     * creating a recipeInventory
     */
    public long createRecipeInventory(RecipeInventory recipeInventory){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_RECIPE_ID, recipeInventory.getRecipe_id());
        values.put(KEY_INVENTORY_ID, recipeInventory.getInventory_id());
        values.put(KEY_QUANTITY, recipeInventory.getQuantity());

        //insert row
        long recipeInventory_id = db.insert(TABLE_RECIPEINVETORY, null, values);
        return recipeInventory_id;
    }

    /**
     * Fetching a recipeInventory
     */
    public RecipeInventory getRecipeInventory(long recipeInventory_id){
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM "
                + TABLE_RECIPEINVETORY + " WHERE "
                + KEY_ID + " = "
                + recipeInventory_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if(c != null)
            c.moveToFirst();

        RecipeInventory recipeInventory = new RecipeInventory();
        recipeInventory.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        recipeInventory.setRecipe_id(c.getInt(c.getColumnIndex(KEY_RECIPE_ID)));
        recipeInventory.setInventory_id(c.getInt(c.getColumnIndex(KEY_INVENTORY_ID)));
        recipeInventory.setQuantity(c.getInt(c.getColumnIndex(KEY_QUANTITY)));

        return recipeInventory;
    }

    /**
     * Fetching all recipeInventory
     */
    public List<RecipeInventory> getAllRecipeInventory(){
        List<RecipeInventory> recipeInventories = new ArrayList<RecipeInventory>();
        String selectQuery = "SELECT * FROM "
                + TABLE_RECIPEINVETORY;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if(c.moveToFirst()) {
            do {
                RecipeInventory recipeInventory = new RecipeInventory();
                recipeInventory.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                recipeInventory.setRecipe_id(c.getInt(c.getColumnIndex(KEY_RECIPE_ID)));
                recipeInventory.setInventory_id(c.getInt(c.getColumnIndex(KEY_INVENTORY_ID)));
                recipeInventory.setQuantity(c.getInt(c.getColumnIndex(KEY_QUANTITY)));

                // adding to recipeInventories
                recipeInventories.add(recipeInventory);
            } while(c.moveToNext());
        }
        return recipeInventories;
    }

    /**
     * Update a recipeInventory
     */
    public int updateRecipeInventory(RecipeInventory recipeInventory){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_RECIPE_ID, recipeInventory.getRecipe_id());
        values.put(KEY_INVENTORY_ID, recipeInventory.getInventory_id());
        values.put(KEY_QUANTITY, recipeInventory.getQuantity());

        //updating row
        return db.update(TABLE_RECIPEINVETORY, values,
                KEY_ID + " = ?",
                new String[] {String.valueOf(recipeInventory.getId())});

    }

    /**
     * Delete a recipeInventory
     */
    public void deleteRecipeInventory(long recipeInventory_id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RECIPEINVETORY,
                KEY_ID + " = ?",
                new String[] {String.valueOf(recipeInventory_id)});
    }

}
