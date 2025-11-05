package com.example.drewglasser_brown;

/*
 * Name: Drew Glasser-Brown
 * Purpose: This is the helper class that will deal with storing and retrieving meals in from our database
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MealDBHelper extends SQLiteOpenHelper {
    private static String DB_NAME = "meals.db";
    private static String TABLE_NAME = "meals";
    private static int DB_VERSION = 1;
    public static String ID = "_id";
    public static String RECIPE_ID = "recipe_id";
    public static String MEAL_TYPE = "mealType";
    public static String DATE = "date";
    public SQLiteDatabase sqLiteDatabase;

    public MealDBHelper(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * Opens the database
     * @throws SQLException
     */
    public void open() throws SQLException
    {
        this.sqLiteDatabase = getWritableDatabase();
    }

    /**
     * Closes the database
     */
    public void close()
    {
        sqLiteDatabase.close();
    }

    /**
     * This method will create a table for the database
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String sCreate = "create table " +
                TABLE_NAME + " (" +
                ID + " integer primary key autoincrement, " +
                RECIPE_ID + " integer not null, " +
                MEAL_TYPE + " text not null, " +
                DATE + " text not null);";
        //Execute the create table sql statement
        db.execSQL(sCreate);
    }

    /**
     * If the table ever needed to be upgraded this method would recreate the database and drop the previous table
     * @param db The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop the existing table if it exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        //Use the existing onCreate code to recreate the table
        onCreate(db);
    }

    /**
     * This method will given a meal, store a meal in the database. We are storing the date object as a string
     * in order to convert the date into a string we are using the SimpleDateFormat class to store our dates in the form "mm/dd/yyyy"
     * @param meal
     * @return
     */
    public long createMeal(Meal meal)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        String date = sdf.format(meal.getDate());
        ContentValues cvs = new ContentValues();
        cvs.put(RECIPE_ID, meal.getRecipeId());
        cvs.put(MEAL_TYPE, meal.getMealType());
        cvs.put(DATE, date);

        //Saving the meal
        long autoId = sqLiteDatabase.insert(TABLE_NAME, null, cvs);
        meal.setId(autoId);
        return autoId;
    }

    /**
     * This method will query for a Cursor of meals and then convert that into an arraylist to be returned
     * @return
     */
    public ArrayList<Meal> getAllMeals()
    {
        //The fields we want from the table
        String [] sFields = new String [] {ID, RECIPE_ID, MEAL_TYPE, DATE};
        //The query itself
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, sFields, null, null, null, null, null);
        //Setting up the simpledateformat object again to parse our strings back into date objects
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

        ArrayList<Meal> meals = new ArrayList<>();
        //If the cursor has any records
        if(cursor.moveToFirst())
        {
            do
            {
                try {
                    //Parsing the date
                    Date date = sdf.parse(cursor.getString(3));
                    //Creating our meals
                    Meal meal = new Meal(Long.parseLong(cursor.getString(0)),
                            Long.parseLong(cursor.getString(1)),
                            date,
                            cursor.getString(2)
                            );
                    meals.add(meal);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

            } while (cursor.moveToNext());
        }

        return meals;
    }
}
