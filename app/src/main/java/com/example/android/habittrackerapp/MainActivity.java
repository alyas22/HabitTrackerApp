package com.example.android.habittrackerapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.habittrackerapp.data.HabitDbHelper;

import java.util.ArrayList;

import static com.example.android.habittrackerapp.data.HabitContract.HabitEntry;
import static com.example.android.habittrackerapp.data.HabitContract.HabitEntry.TABLE_NAME;

public class MainActivity extends AppCompatActivity {
    private HabitAdapter mAdapter;
    ArrayList<Habit> habitList;
    private ListView habitListView;
    private HabitDbHelper mDbHelper;
    Habit habit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        habitListView = (ListView) findViewById(R.id.list_view);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });
        mDbHelper = new HabitDbHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    public Cursor query() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String[] projection = {
                HabitEntry._ID,
                HabitEntry.COLUMN_NAME,
                HabitEntry.COLUMN_REPEAT};

        Cursor cursor = db.query(
                HabitEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);
        return cursor;
    }

    private void displayDatabaseInfo() {

        Cursor cursor = query();

        TextView displayView = (TextView) findViewById(R.id.text_view);
        try {

            displayView.setText("The Habit table contains " + cursor.getCount() + " habits.\n\n");

            int idColumnIndex = cursor.getColumnIndex(HabitEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_NAME);
            int repeatColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_REPEAT);
            mAdapter = new HabitAdapter(this, new ArrayList<Habit>());

            habitListView.setAdapter(mAdapter);
            int rows = cursor.getCount();
            if (rows == 0) {
                Toast.makeText(MainActivity.this, R.string.database_empty, Toast.LENGTH_SHORT).show();
            } else {
                while (cursor.moveToNext()) {
                    int currentID = cursor.getInt(idColumnIndex);
                    String currentName = cursor.getString(nameColumnIndex);
                    String currentRepeat = cursor.getString(repeatColumnIndex);

                    habitList = new ArrayList<Habit>();
                    habit = new Habit(currentID, currentName, currentRepeat);
                    mAdapter.add(habit);
                }
            }

        } finally {
            cursor.close();
        }
    }

    private void insertHabit() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(HabitEntry.COLUMN_NAME, "Swim");
        values.put(HabitEntry.COLUMN_REPEAT, 1);

        long newRowId = db.insert(TABLE_NAME, null, values);
    }

    private void deleteHabit() {

        HabitDbHelper mDbHelper = new HabitDbHelper(this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_insert_dummy_data:
                insertHabit();
                displayDatabaseInfo();
                return true;

            case R.id.action_delete_all_entries:
                deleteHabit();
                displayDatabaseInfo();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
