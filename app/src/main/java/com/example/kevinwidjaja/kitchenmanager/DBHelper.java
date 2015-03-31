package com.example.kevinwidjaja.kitchenmanager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by kevinwidjaja on 3/30/15.
 */
public class DBHelper extends SQLiteOpenHelper{

    //Database version
    private static final int DATABASE_VERSION = 1;

    //Database name
    private static final String DATABASE_NAME = "kitchenManager";

    //Table name
    public static final String TABLE_SHOP = "shoppingList";
    public static final String TABLE_INV = "inventoryList";

    //Common column name
    public static final String ID_COLUMN = "id";
    public static final String ITEM_COLUMN = "item";

    //Table create statements
    //Shopping list table
    private static final String CREATE_TABLE_SHOP = String.format(
            "CREATE TABLE %s (" + " %s integer primary key autoincrement, " +
                    " %s text)",
            TABLE_SHOP,ID_COLUMN,ITEM_COLUMN);
    //Inventory list table
    private static final String CREATE_TABLE_INV = String.format(
            "CREATE TABLE %s (" + " %s integer primary key autoincrement, " +
                    " %s text)",
            TABLE_INV,ID_COLUMN,ITEM_COLUMN);

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SHOP);
        db.execSQL(CREATE_TABLE_INV);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF IT EXISTS " + TABLE_SHOP);
        db.execSQL("DROP TABLE IF IT EXISTS " + TABLE_INV);
        onCreate(db);
    }

}
