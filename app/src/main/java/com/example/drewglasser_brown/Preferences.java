package com.example.drewglasser_brown;

/*
 * Name: Drew Glasser-Brown
 * Purpose: This activity is where shared preferences are stored.
 */

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class Preferences extends AppCompatActivity {
    private int hour, minutes;
    private EditText etTimeBreakfast;
    private EditText etTimeLunch;
    private EditText etTimeDinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        etTimeBreakfast = findViewById(R.id.etTimeBreakfast);
        etTimeLunch = findViewById(R.id.etTimeLunch);
        etTimeDinner = findViewById(R.id.etTimeDInner);
        //Loading in the preferences into the edit text, otherwise they are defaults
        SharedPreferences prefs = this.getSharedPreferences(this.getPackageName(), Context.MODE_PRIVATE);
        etTimeBreakfast.setText(prefs.getString("breakfast", "07:00"));
        etTimeLunch.setText(prefs.getString("lunch", "11:30"));
        etTimeDinner.setText(prefs.getString("dinner", "17:00"));
    }

    /**
     * On button press return to the home page
     * @param v
     */
    public void onCancelPressed(View v)
    {
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
    }

    /**
     * Each one of these methods calls a TimePicker which will open a window for the user to specify a time
     * this is better than using the edittext time thing in layouts because it doesn't actually validate anything
     * related to time. Doing it this way we can validate that the user enters a time.
     * It is important to make sure that the edittexts in the layout are not focusable though so that
     * the keyboard doesnt interfere with the timer window
     * @param v
     */
    public void onETBreakfastPressed(View v)
    {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                hour = hourOfDay;
                minutes = minute;
                etTimeBreakfast.setText(String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute));
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, AlertDialog.THEME_HOLO_DARK, onTimeSetListener, hour, minutes, true);
        timePickerDialog.setTitle("Pick a Time");
        timePickerDialog.show();
    }

    /**
     * A timepicker for the lunch edittext
     * @param v
     */
    public void onETLunchPressed(View v)
    {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                hour = hourOfDay;
                minutes = minute;
                etTimeLunch.setText(String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute));
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, AlertDialog.THEME_HOLO_DARK, onTimeSetListener, hour, minutes, true);
        timePickerDialog.setTitle("Pick a Time");
        timePickerDialog.show();
    }

    /**
     * A time picker for the dinner edit text
     * @param v
     */
    public void onETDinnerPressed(View v)
    {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                hour = hourOfDay;
                minutes = minute;
                etTimeDinner.setText(String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute));
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, AlertDialog.THEME_HOLO_DARK, onTimeSetListener, hour, minutes, true);
        timePickerDialog.setTitle("Pick a Time");
        timePickerDialog.show();
    }

    /**
     * This method will store all the times in sharedpreferences
     * @param v
     */
    public void onSavePressed(View v)
    {
        //Get shared preferences and the editor to edit them
        SharedPreferences prefs = this.getSharedPreferences(this.getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        if (!etTimeBreakfast.getText().toString().isEmpty())
        {
            editor.putString("breakfast", etTimeBreakfast.getText().toString());
        }
        else
        {
            editor.putString("breakfast", "07:00");
        }
        if (!etTimeLunch.getText().toString().isEmpty())
        {
            editor.putString("lunch", etTimeLunch.getText().toString());
        }
        else
        {
            editor.putString("lunch", "11:30");
        }
        if (!etTimeLunch.getText().toString().isEmpty())
        {
            editor.putString("dinner", etTimeDinner.getText().toString());
        }
        else
        {
            editor.putString("dinner", "17:00");
        }
        // save changes
        editor.commit();

        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
    }


}