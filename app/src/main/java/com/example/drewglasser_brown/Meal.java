package com.example.drewglasser_brown;

/*
 * Name: Drew Glasser-Brown
 * Purpose: This class is the model for the Meal data to be stored in the database. It has an id, recipeId, mealtype, and date. As well as getters and setters
 */

import java.util.Date;

public class Meal {
    private long id;
    private long recipeId;
    private String mealType;
    private Date date;

    /**
     * For when we want to save a meal to the database and do not have an id
     * @param recipeId
     * @param date
     * @param mealType
     */
    public Meal(long recipeId, Date date, String mealType)
    {
        this.date = date;
        this.recipeId = recipeId;
        this.mealType = mealType;
    }

    /**
     * For when we are reading from the database and the object already has an id
     * @param id
     * @param recipeId
     * @param date
     * @param mealType
     */
    public Meal(long id, long recipeId, Date date, String mealType)
    {
        this(recipeId, date, mealType);
        this.id = id;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(long recipeId) {
        this.recipeId = recipeId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }
}
