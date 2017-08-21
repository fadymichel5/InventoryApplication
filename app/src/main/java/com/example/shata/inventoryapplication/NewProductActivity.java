package com.example.shata.inventoryapplication;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shata.inventoryapplication.data.ItemContract;
import com.example.shata.inventoryapplication.data.ItemDbHelper;

import static android.R.attr.name;
import static com.example.shata.inventoryapplication.R.string.price;
import static com.example.shata.inventoryapplication.R.string.quantity;

public class NewProductActivity extends AppCompatActivity {


    private static final int SELECT_PICTURE = 1;
    String selectedImagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);

        final EditText name_edit_text = (EditText) findViewById(R.id.edit_name);
        final EditText quantity_edit_text = (EditText) findViewById(R.id.edit_quantity);
        final EditText price_edit_text = (EditText) findViewById(R.id.edit_price);
        Button select_photo_button = (Button) findViewById(R.id.select_photo);
        Button done_button = (Button) findViewById(R.id.done_add_new);
        final EditText number_edit_txt = (EditText) findViewById(R.id.edit_number);

        select_photo_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,
                        "Select Picture"), SELECT_PICTURE);


            }
        });


        done_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name_string = name_edit_text.getText().toString().trim();
                String quantity_string = quantity_edit_text.getText().toString().trim();
                String price_string = price_edit_text.getText().toString().trim();
                String number_string = number_edit_txt.getText().toString().trim();
                if (TextUtils.isEmpty(name_string) || TextUtils.isEmpty(quantity_string) || TextUtils.isEmpty(quantity_string) || TextUtils.isEmpty(number_string))
                    Toast.makeText(getApplicationContext(), "Missing Data", Toast.LENGTH_SHORT).show();
                else {
                    ContentValues values = new ContentValues();
                    values.put(ItemContract.ItemEntry.COLUMN_ITEM_NUMBER, number_string);
                    values.put(ItemContract.ItemEntry.COLUMN_ITEM_NAME, name_string);
                    values.put(ItemContract.ItemEntry.COLUMN_ITEM_PRICE, Integer.parseInt(price_string));
                    values.put(ItemContract.ItemEntry.COLUMN_ITEM_QUANTITY, Integer.parseInt(quantity_string));
                    values.put(ItemContract.ItemEntry.COLUMN_ITEM_IMAGE_PATH, selectedImagePath);
                    Uri newUri = getContentResolver().insert(ItemContract.ItemEntry.CONTENT_URI, values);
                    if (newUri == null)
                        Toast.makeText(getApplicationContext(), "the insertion was unsuccessful", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getApplicationContext(), "the insertion was successful", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(NewProductActivity.this, MainActivity.class);
                    startActivity(intent);


                }

            }
        });


    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = selectedImageUri.toString();

            }
        }
    }

    /**
     * helper to retrieve the path of an image URI
     */
    public String getPath(Uri uri) {
        // just some safety built in
        if (uri == null) {
            Context context = getApplicationContext();
            CharSequence text = "You Should enter a photo";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return null;
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(column_index);
            cursor.close();
            return path;
        }
        // this is our fallback here
        return uri.getPath();
    }

}





