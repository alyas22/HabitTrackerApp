package com.example.android.habittrackerapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.habittrackerapp.data.HabitDbHelper;

import static com.example.android.habittrackerapp.data.HabitContract.HabitEntry;
import static com.example.android.habittrackerapp.data.HabitContract.HabitEntry.COLUMN_NAME;


public class EditorActivity extends AppCompatActivity {

    private EditText mNameEditText;
    private EditText mRepeatEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editor);

        mNameEditText = (EditText) findViewById(R.id.edit_text_name);
        mRepeatEditText = (EditText) findViewById(R.id.edit_text_repeat);
    }

    private void insertHabit() {
        String nameString = mNameEditText.getText().toString();
        String repeatString = mRepeatEditText.getText().toString();

        if (nameString.trim().matches("") && repeatString.trim().matches("")) {
            Toast.makeText(this, R.string.enter_habit, Toast.LENGTH_SHORT).show();
        } else if (repeatString.trim().matches("")) {
            Toast.makeText(this, R.string.enter_repeat, Toast.LENGTH_SHORT).show();
        } else {
            int repeat = Integer.parseInt(repeatString);
            HabitDbHelper mDbHelper = new HabitDbHelper(this);
            SQLiteDatabase db = mDbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME, nameString);
            values.put(HabitEntry.COLUMN_REPEAT, repeat);

            long newRowId = db.insert(HabitEntry.TABLE_NAME, null, values);

            if (newRowId == -1) {
                Toast.makeText(this, "Error with saving ", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Habit saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
            }
            finish();
        }
    }

    private void reset() {
        mNameEditText.setText("");
        mRepeatEditText.setText("");
        // hide keyboard
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(mNameEditText.getWindowToken(), 0);
        in.hideSoftInputFromWindow(mRepeatEditText.getWindowToken(), 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                insertHabit();
                return true;

            case R.id.action_reset:
                reset();
                return true;

            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
