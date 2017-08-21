package com.example.shata.inventoryapplication;

/**
 * Created by Shata on 03/08/2017.
 */


import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shata.inventoryapplication.data.ItemContract;

import static android.R.attr.id;
import static android.R.attr.name;
import static com.example.shata.inventoryapplication.R.string.price;
import static com.example.shata.inventoryapplication.R.string.quantity;

public class ItemCursorAdapter extends CursorAdapter {
    Cursor cursor_now;

    public ItemCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, final Cursor cursor) {
        TextView name_text_view = (TextView) view.findViewById(R.id.name_of_the_product);
        final TextView quantity_text_view = (TextView) view.findViewById(R.id.quantity_of_the_product);
        TextView price_text_view = (TextView) view.findViewById(R.id.price_of_the_product);
        Button sell_button = (Button) view.findViewById(R.id.sell_button);


        int nameColumnIndex = cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_ITEM_NAME);
        final int quantityColumnIndex = cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_ITEM_QUANTITY);
        int priceColumnIndex = cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_ITEM_PRICE);
        int imageColumnIndex = cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_ITEM_IMAGE_PATH);

        final String itemName = cursor.getString(nameColumnIndex);
        final String itemImage = cursor.getString(imageColumnIndex);
        final int itemQuantity = cursor.getInt(quantityColumnIndex);
        final int itemPrice = cursor.getInt(priceColumnIndex);


        name_text_view.setText(itemName);
        quantity_text_view.setText(Integer.toString(itemQuantity));
        price_text_view.setText(Integer.toString(itemPrice));
        final Long id = cursor.getLong(cursor.getColumnIndex(ItemContract.ItemEntry._ID));


        final Uri mUri = ContentUris.withAppendedId(ItemContract.ItemEntry.CONTENT_URI,
                id);


        sell_button.setTag(R.string.quantity_id, cursor.getInt(quantityColumnIndex));

        sell_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity_now = (int) v.getTag(R.string.quantity_id);


                if (quantity_now > 0) {
                    quantity_now--;

                    ContentValues values = new ContentValues();
                    values.put(ItemContract.ItemEntry.COLUMN_ITEM_QUANTITY, quantity_now);
                    values.put(ItemContract.ItemEntry.COLUMN_ITEM_NAME, itemName);
                    values.put(ItemContract.ItemEntry.COLUMN_ITEM_PRICE, itemPrice);
                    values.put(ItemContract.ItemEntry.COLUMN_ITEM_IMAGE_PATH, itemImage);

                    int rowsUpdated = v.getContext().getContentResolver().update(mUri, values, null, null);
                    if (rowsUpdated == 0) {
                        Toast.makeText(v.getContext(), "Error occurred",
                                Toast.LENGTH_LONG).show();
                    } else {
                        quantity_text_view.setText(String.valueOf(quantity_now));
                    }


                }


            }


        });


    }


}