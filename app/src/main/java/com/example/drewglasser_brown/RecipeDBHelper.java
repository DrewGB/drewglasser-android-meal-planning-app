package com.example.drewglasser_brown;

/*
 * Name: Drew Glasser-Brown
 * Purpose: This helper class is responsible for saving and retrieving recipes from the database
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class RecipeDBHelper extends SQLiteOpenHelper {
    private static String DB_NAME = "recipes.db";
    private static String TABLE_NAME = "recipes";
    private static int DB_VERSION = 1;
    public static String ID = "_id";
    public static String TITLE = "title";
    public static String IMAGE_PATH = "imagePath";
    public static String NOTES = "notes";
    public SQLiteDatabase sqLiteDatabase;

    public RecipeDBHelper(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public void open() throws SQLException
    {
        this.sqLiteDatabase = getWritableDatabase();
    }

    public void close()
    {
        sqLiteDatabase.close();
    }

    /**
     * This method will create the table for our database
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String sCreate = "create table " +
                TABLE_NAME + " (" +
                ID + " integer primary key autoincrement, " +
                TITLE + " text not null, " +
                IMAGE_PATH + " text not null, " +
                NOTES + " text not null);";
        //Execute the create table sql statement
        db.execSQL(sCreate);
    }

    /**
     * If the version for the database changes than this will drop the table and create a new one
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
     * This method will save a recipe to the database
     * @param recipe
     * @return
     */
    public long createRecipe(Recipe recipe)
    {
        //The values to be stored
        ContentValues cvs = new ContentValues();
        cvs.put(TITLE, recipe.getTitle());
        cvs.put(IMAGE_PATH, recipe.getImagePath());
        cvs.put(NOTES, recipe.getNotes());

        //Actually save the recipe
        long autoId = sqLiteDatabase.insert(TABLE_NAME, null, cvs);
        recipe.setId(autoId);
        return autoId;
    }

    /**
     * This class will query for all of the recipes, convert the cursor into an ArrayList and return it.
     * @return
     */
    public ArrayList<Recipe> getAllRecipes()
    {
        String [] sFields = new String [] {ID, TITLE, IMAGE_PATH, NOTES};
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, sFields, null, null, null, null, null);
        ArrayList<Recipe> recipes = new ArrayList<>();
        if(cursor.moveToFirst())
        {
            do
            {
                Recipe recipe = new Recipe(Long.parseLong(cursor.getString(0)),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3));
                recipes.add(recipe);
            } while (cursor.moveToNext());
        }

        return recipes;
    }

    /**
     * This method will read in a specific recipe
     * @param id of the recipe to be retrieved
     * @return
     */
    public Recipe getRecipe(long id)
    {
        String [] sFields = new String [] {ID, TITLE, IMAGE_PATH, NOTES};
        //The query now has a where paramater for the id
        Cursor fpCursor = sqLiteDatabase.query(true, TABLE_NAME, sFields, ID + " = "
                + id, null, null, null, null, null);
        if(fpCursor.moveToFirst())
        {
            //Don't need to loop this time. Only one record.
            return new Recipe(Long.parseLong(fpCursor.getString(0)),
                    fpCursor.getString(1),
                    fpCursor.getString(2),
                    fpCursor.getString(3));
        }
        return null;
    }
}
