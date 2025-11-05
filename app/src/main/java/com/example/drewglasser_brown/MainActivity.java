package com.example.drewglasser_brown;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    /*
     * Name: Drew Glasser-Brown
     * Purpose: This activity will act as the home page where the user can navigate to the different parts of the app. it also is where they can view
     * what their meals will be for the day
     */

    private Recipe breakfast = null;
    private Recipe lunch = null;
    private Recipe dinner = null;

    private Button btnBreakfast;
    private Button btnLunch;
    private Button btnDinner;
    private TextView txtBreakfast;
    private TextView txtLunch;
    private TextView txtDinner;

    private RecipeDBHelper recipeDBHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnBreakfast = findViewById(R.id.btnBreakfast);
        btnLunch = findViewById(R.id.btnLunch);
        btnDinner = findViewById(R.id.btnDinner);
        txtBreakfast = findViewById(R.id.txtBreakfast);
        txtLunch = findViewById(R.id.txtLunch);
        txtDinner = findViewById(R.id.txtDinner);

        MealDBHelper mealDBHelper = new MealDBHelper(this);
        mealDBHelper.open();
        ArrayList<Meal> meals = mealDBHelper.getAllMeals();
        //Check if there are meals
        if (!meals.isEmpty())
        {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            sdf.format(new Date());
            for (Meal meal : meals)
            {
                //Check if there are any meals saved for today
                if (sdf.format(new Date()).equals(sdf.format(meal.getDate())))
                {
                    //Figure out if it is breakfast, lunch or dinner and deal with it accordingly
                    recipeDBHelper = new RecipeDBHelper(this);
                    if (meal.getMealType().equals("breakfast"))
                    {
                        hasBreakfast(meal);
                    }
                    switch (meal.getMealType())
                    {
                        case "breakfast":
                            hasBreakfast(meal);
                            break;
                        case "lunch":
                            hasLunch(meal);
                            break;
                        case "dinner":
                            hasDinner(meal);
                            break;
                    }
                }
            }
        }
        mealDBHelper.close();

    }

    /**
     * This method will simply pass to the AddARecipeActivity
     * @param v
     */
    public void addRecipePressed(View v)
    {
        Intent intent = new Intent(this, AddARecipeActivity.class);
        this.startActivity(intent);
    }

    /**
     * This method will simply pass to the Preferences Activity
     * @param v
     */
    public void onNotificationPreferencesPressed(View v)
    {
        Intent intent = new Intent(this, Preferences.class);
        this.startActivity(intent);
    }

    /**
     * If they have a breakfast set for today then we need to query for that recipe and display it for the user. And set the button to view recipe
     * @param meal
     */
    public void hasBreakfast(Meal meal)
    {
        recipeDBHelper.open();
        breakfast = recipeDBHelper.getRecipe(meal.getRecipeId());
        recipeDBHelper.close();
        txtBreakfast.setText(breakfast.getTitle());
        btnBreakfast.setText("View Recipe");
    }

    /**
     * If they have a lunch set for today then we need to query for that recipe and display it for the user. And set the button to view recipe
     * @param meal
     */
    public void hasLunch(Meal meal)
    {
        recipeDBHelper.open();
        lunch = recipeDBHelper.getRecipe(meal.getRecipeId());
        recipeDBHelper.close();
        txtLunch.setText(lunch.getTitle());
        btnLunch.setText("View Recipe");
    }

    /**
     * If they have a dinner set for today then we need to query for that recipe and display it for the user. And set the button to view recipe
     * @param meal
     */
    public void hasDinner(Meal meal)
    {
        recipeDBHelper.open();
        dinner = recipeDBHelper.getRecipe(meal.getRecipeId());
        recipeDBHelper.close();
        txtDinner.setText(dinner.getTitle());
        btnDinner.setText("View Recipe");
    }

    /**
     * Depending on if they have a breakfast set or not we need to send them to either the choose recipe to choose a breakfast,
     * or view recipe and pass in the recipe for them to view its details
     * @param v
     */
    public void onBreakfastPressed(View v)
    {
        if (breakfast != null)
        {
            Intent intent = new Intent(this, ViewRecipeActivity.class);
            intent.putExtra("recipe", breakfast);
            this.startActivity(intent);
        }
        else
        {
            Intent intent = new Intent(this, ChooseRecipe.class);
            intent.putExtra("mealType", "breakfast");
            this.startActivity(intent);
        }
    }

    /**
     * Depending on if they have a lunch set or not we need to send them to either the choose recipe to choose a lunch,
     * or view recipe and pass in the recipe for them to view its details
     * @param v
     */
    public void onLunchPressed(View v)
    {
        if (lunch != null)
        {
            Intent intent = new Intent(this, ViewRecipeActivity.class);
            intent.putExtra("recipe", lunch);
            this.startActivity(intent);
        }
        else
        {
            Intent intent = new Intent(this, ChooseRecipe.class);
            intent.putExtra("mealType", "lunch");
            this.startActivity(intent);
        }
    }

    /**
     * Depending on if they have a dinner set or not we need to send them to either the choose recipe to choose a dinner,
     * or view recipe and pass in the recipe for them to view its details
     * @param v
     */
    public void onDinnerPressed(View v)
    {
        if (dinner != null)
        {
            Intent intent = new Intent(this, ViewRecipeActivity.class);
            intent.putExtra("recipe", dinner);
            this.startActivity(intent);
        }
        else
        {
            Intent intent = new Intent(this, ChooseRecipe.class);
            intent.putExtra("mealType", "dinner");
            this.startActivity(intent);
        }
    }
}