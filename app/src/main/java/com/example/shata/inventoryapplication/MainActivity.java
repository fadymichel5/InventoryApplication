package com.example.shata.inventoryapplication;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.shata.inventoryapplication.data.ItemContract;
import com.example.shata.inventoryapplication.data.ItemDbHelper;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    ListView listView;
    //private Cursor cursor;
    private static final int ITEM_LOADER = 0;
    ItemCursorAdapter itemCursorAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.list_view);
        Button AddButton = (Button) findViewById(R.id.add_button);

        View emptyView = findViewById(R.id.empty_view);
        listView.setEmptyView(emptyView);
        itemCursorAdapter = new ItemCursorAdapter(this, null);
        listView.setAdapter(itemCursorAdapter);
        getLoaderManager().initLoader(ITEM_LOADER, null, this);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                Uri currentPetUri = ContentUris.withAppendedId(ItemContract.ItemEntry.CONTENT_URI, id);
                intent.setData(currentPetUri);
                startActivity(intent);
            }
        });


        AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if add product pressed
                Intent intent = new Intent(MainActivity.this, NewProductActivity.class);
                startActivity(intent);
            }
        });


    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] project = {
                ItemContract.ItemEntry._ID,
                ItemContract.ItemEntry.COLUMN_ITEM_NAME,
                ItemContract.ItemEntry.COLUMN_ITEM_QUANTITY,
                ItemContract.ItemEntry.COLUMN_ITEM_PRICE,
                ItemContract.ItemEntry.COLUMN_ITEM_IMAGE_PATH

        };
        return new CursorLoader(this, ItemContract.ItemEntry.CONTENT_URI, project, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        itemCursorAdapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        itemCursorAdapter.swapCursor(null);

    }
}
