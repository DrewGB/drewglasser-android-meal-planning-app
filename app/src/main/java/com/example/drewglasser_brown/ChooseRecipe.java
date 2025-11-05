package com.example.drewglasser_brown;

/*
 * Name: Drew Glasser-Brown
 * Purpose: This activity will, given a mealtype, store a meal after displaying all the recipes saved in a recyclerview
 */

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChooseRecipe extends AppCompatActivity {

    private RecyclerView recipeList;
    private RecipeListAdapter adapter;
    private ArrayList<Recipe> recipes;
    private String mealType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_recipe);


        recipeList = findViewById(R.id.rvRecipes);
        RecipeDBHelper db = new RecipeDBHelper(this);
        //Getting the mealtype so we know what kind of meal it is.
        Intent intent = getIntent();
        mealType = intent.getStringExtra("mealType");

        db.open();
        //Getting all the recipes from the database
        recipes = db.getAllRecipes();
        db.close();

        //Configuring the recylerview
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recipeList.setLayoutManager(manager);
        adapter = new RecipeListAdapter(this, recipes);
        adapter.setClickListener(new RecipeListAdapter.ItemClickListener() {
            // Passes the position of the item they clicked on to saveRecipe
            @Override
            public void onItemClick(View view, int position) {
                saveRecipe(position);
            }
        });
        recipeList.setAdapter(adapter);
    }

    /**
     * This method will create a meal and store it to the database
     * it will also create a notification for a time based on the mealtype and the sharedpreferences that the user chose
     * @param position
     */
    public void saveRecipe(int position)
    {
        Recipe recipe = recipes.get(position);
        MealDBHelper mealDb = new MealDBHelper(this);
        mealDb.open();
        Date date = new Date();
        Meal meal = new Meal(recipe.getId(), date, mealType);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/YYYY");
        //Saving the meal to the database
        mealDb.createMeal(meal);
        mealDb.close();

        createNotification();

        //Back to the main activity
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
    }

    /**
     * This method will create a notification and notify the user that it is meal time based off of what they set for their meal times in shared preferences
     */
    public void createNotification()
    {
        //Using our MealService class which has the notification
        Intent intent1 = new Intent(getBaseContext(), MealService.class);
        // run the service
        // wrap the intent in a pending intent, which can run in thefuture
        PendingIntent pendingIntent = PendingIntent.getService(getBaseContext(), 1, intent1, PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        SharedPreferences prefs = this.getSharedPreferences(this.getPackageName(), Context.MODE_PRIVATE);
        String value = "";
        if (mealType.equals("breakfast"))
        {
            value = prefs.getString("breakfast", "07:00");
        }
        if (mealType.equals("lunch"))
        {
            value = prefs.getString("lunch", "11:30");
        }
        if (mealType.equals("dinner"))
        {
            value = prefs.getString("dinner", "17:00");
        }

        // schedule the pending intent using the alarm service
        AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        try {
            Date requestedTime = sdf.parse(value);
            //Calculating the time for the notification
            long delay = requestedTime.getTime() - System.currentTimeMillis();
            am.set(AlarmManager.RTC_WAKEUP, delay, pendingIntent);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }



    }
}