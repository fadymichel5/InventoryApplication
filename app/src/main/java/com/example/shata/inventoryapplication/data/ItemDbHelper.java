package com.example.shata.inventoryapplication.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.example.shata.inventoryapplication.data.ItemContract;

import static android.R.attr.version;

/**
 * Created by Shata on 03/08/2017.
 */

public class ItemDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "inventory.db";
    private static final int DATABASE_VERSION = 1;

    public ItemDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_ITEMS_TABLE = "CREATE TABLE " + ItemContract.ItemEntry.TABLE_NAME + " ("
                + ItemContract.ItemEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ItemContract.ItemEntry.COLUMN_ITEM_NAME + " TEXT NOT NULL, "
                + ItemContract.ItemEntry.COLUMN_ITEM_QUANTITY + " INTEGER NOT NULL DEFAULT 0, "
                + ItemContract.ItemEntry.COLUMN_ITEM_PRICE + " INTEGER NOT NULL, "
                + ItemContract.ItemEntry.COLUMN_ITEM_NUMBER + " TEXT NOT NULL, "
                + ItemContract.ItemEntry.COLUMN_ITEM_IMAGE_PATH + " TEXT);";

        db.execSQL(SQL_CREATE_ITEMS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
