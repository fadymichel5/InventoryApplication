package com.example.shata.inventoryapplication.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Shata on 03/08/2017.
 */

public final class ItemContract {

    public static final String CONTENT_AUTHORITY = "com.example.shata.inventoryapplication";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH = "items";

    private ItemContract() {
    }

    public static final class ItemEntry implements BaseColumns {

        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH;

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH);


        public final static String TABLE_NAME = "items";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_ITEM_NAME = "name";
        public final static String COLUMN_ITEM_QUANTITY = "quantity";
        public final static String COLUMN_ITEM_PRICE = "price";
        public final static String COLUMN_ITEM_IMAGE_PATH = "image";
        public final static String COLUMN_ITEM_NUMBER = "number";


    }
}
