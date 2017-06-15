package com.example.providerphone;


import android.net.Uri;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;



public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener {
    private SimpleCursorAdapter adapter;
    private ListView listView;
    private Cursor cursor;
    private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setListView();
        if (checkSelfPermission( Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions( new String[]{Manifest.permission.READ_CONTACTS}, 100);
        } else {
            loadData();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadData();
            }
        }
    }

    private void setListView() {
        listView = (ListView) findViewById(R.id.listId);
        adapter = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_2,cursor,new String[]{
                Phone.NUMBER,
                Phone.DISPLAY_NAME},
                new int[]{android.R.id.text1,android.R.id.text2},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    public void loadData() {
       /* cursor = getContentResolver().query(Phone.CONTENT_URI, null, null, null, null);
        adapter.swapCursor(cursor);*/
        id = 1;
        Bundle bundle = new Bundle();
        bundle.putParcelable("uri", Phone.CONTENT_URI);
        LoaderManager.LoaderCallbacks<Cursor> loaderCallbacks = this;
        getSupportLoaderManager().initLoader(id, bundle, loaderCallbacks);
    }
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri uri = args.getParcelable("uri");

        return new CursorLoader(this,uri,null,null,null,null);
    }



    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Cursor cursor = (Cursor) parent.getItemAtPosition(position);
        String name = cursor.getString(cursor.getColumnIndex(Phone.DISPLAY_NAME));
        Log.d("MainActivity", name);
    }
}
