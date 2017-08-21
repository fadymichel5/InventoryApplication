package com.example.shata.inventoryapplication;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;

import com.example.shata.inventoryapplication.data.ItemContract;

import static android.R.attr.data;
import static android.R.attr.id;
import static android.R.attr.name;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static android.net.Uri.parse;

public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int ITEM_LOADER = 0;
    private Uri currentUri;
    ImageView image_of_the_product;
    TextView name_of_the_product_tv;
    TextView quantity_of_the_product_tv;
    Button add_one_quantity_button;
    Button remove_one_quantity_button;
    TextView price_of_the_product_tv;
    Button order_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        currentUri = intent.getData();

        image_of_the_product = (ImageView) findViewById(R.id.image_of_the_product);
        name_of_the_product_tv = (TextView) findViewById(R.id.name_of_the_product);
        quantity_of_the_product_tv = (TextView) findViewById(R.id.quantity_of_the_product);
        add_one_quantity_button = (Button) findViewById(R.id.add_one_button);
        remove_one_quantity_button = (Button) findViewById(R.id.remove_one_button);
        price_of_the_product_tv = (TextView) findViewById(R.id.price_of_the_product);
        order_button = (Button) findViewById(R.id.order_button);
        Button delete_button = (Button) findViewById(R.id.delete_button);
        Button done_button = (Button) findViewById(R.id.done_button);


        done_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        getSupportLoaderManager().initLoader(ITEM_LOADER, null, this);


        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog diaBox = AskOption(v);
                diaBox.show();

            }
        });

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
        String[] project = {
                ItemContract.ItemEntry._ID,
                ItemContract.ItemEntry.COLUMN_ITEM_NAME,
                ItemContract.ItemEntry.COLUMN_ITEM_QUANTITY,
                ItemContract.ItemEntry.COLUMN_ITEM_PRICE,
                ItemContract.ItemEntry.COLUMN_ITEM_IMAGE_PATH,
                ItemContract.ItemEntry.COLUMN_ITEM_NUMBER
        };
        return new CursorLoader(this, currentUri, project, null, null, null);

    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        if (data == null || data.getCount() < 1) {
            return;
        }
        if (data.moveToFirst()) {
            int idColumnIndex = data.getColumnIndex(ItemContract.ItemEntry._ID);
            int nameColumnIndex = data.getColumnIndex(ItemContract.ItemEntry.COLUMN_ITEM_NAME);
            int quantityColumnIndex = data.getColumnIndex(ItemContract.ItemEntry.COLUMN_ITEM_QUANTITY);
            int priceColumnIndex = data.getColumnIndex(ItemContract.ItemEntry.COLUMN_ITEM_PRICE);
            int imageColumnIndex = data.getColumnIndex(ItemContract.ItemEntry.COLUMN_ITEM_IMAGE_PATH);
            int numberColumnIndex = data.getColumnIndex(ItemContract.ItemEntry.COLUMN_ITEM_NUMBER);

            String name_of_the_product = data.getString(nameColumnIndex);
            int quantity_of_the_product = data.getInt(quantityColumnIndex);
            int price_of_the_product = data.getInt(priceColumnIndex);
            String image_sting = data.getString(imageColumnIndex);
            String number_string = data.getString(numberColumnIndex);

            name_of_the_product_tv.setText(name_of_the_product);
            quantity_of_the_product_tv.setText(String.valueOf(quantity_of_the_product));
            price_of_the_product_tv.setText(String.valueOf(price_of_the_product));
            image_of_the_product.setImageURI(null);
            if (image_sting != null) {
                image_of_the_product.setImageURI(Uri.parse(image_sting));
            }
            remove_one_quantity_button.setTag(R.string.quantity_id, quantity_of_the_product);
            remove_one_quantity_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int quantity_now = (int) v.getTag(R.string.quantity_id);

                    if (quantity_now > 0) {
                        quantity_now--;

                        ContentValues values = new ContentValues();
                        values.put(ItemContract.ItemEntry.COLUMN_ITEM_QUANTITY, quantity_now);

                        int rowsUpdated = v.getContext().getContentResolver().update(currentUri, values, null, null);
                        if (rowsUpdated == 0) {
                            Toast.makeText(v.getContext(), "Error occurred",
                                    Toast.LENGTH_LONG).show();
                        } else {
                            quantity_of_the_product_tv.setText(String.valueOf(quantity_now));
                        }


                    }


                }
            });
            add_one_quantity_button.setTag(R.string.quantity_id, quantity_of_the_product);
            add_one_quantity_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int quantity_now = (int) v.getTag(R.string.quantity_id);

                    quantity_now++;

                    ContentValues values = new ContentValues();
                    values.put(ItemContract.ItemEntry.COLUMN_ITEM_QUANTITY, quantity_now);

                    int rowsUpdated = v.getContext().getContentResolver().update(currentUri, values, null, null);
                    if (rowsUpdated == 0) {
                        Toast.makeText(v.getContext(), "Error occurred",
                                Toast.LENGTH_LONG).show();
                    } else {
                        quantity_of_the_product_tv.setText(String.valueOf(quantity_now));
                    }

                }
            });
            order_button.setTag(R.string.quantity_id, number_string);
            order_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String number = (String) v.getTag(R.string.quantity_id);

                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", number, null));
                    startActivity(intent);
                }
            });


        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private AlertDialog AskOption(final View v) {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                //set message, title, and icon
                .setTitle("Delete")
                .setMessage("Do you want to Delete")


                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        int rowsDeleted = getContentResolver().delete(currentUri, null, null);
                        if (rowsDeleted == 0)
                            Toast.makeText(v.getContext(), "Failed to delete", Toast.LENGTH_SHORT).show();
                        else {
                            Toast.makeText(v.getContext(), "Done delete", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                        dialog.dismiss();
                    }

                })


                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();
        return myQuittingDialogBox;

    }
}
