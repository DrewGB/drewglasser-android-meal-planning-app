package com.example.drewglasser_brown;

/*
 * Name: Drew Glasser-Brown
 * Purpose: This class acts as the model for the recipes that will be stored in the database
 * The class stores id, title, imagepath for the photo, and notes
 */

import java.io.Serializable;

public class Recipe implements Serializable {
    private long id;
    private String title;
    private String imagePath;
    private String notes;

    /**
     * For recipes that havent been saved to the database and don't have an id
     * @param title
     * @param imagePath
     * @param notes
     */
    public Recipe(String title, String imagePath, String notes)
    {
        this.title = title;
        this.imagePath = imagePath;
        this.notes = notes;
    }

    /**
     * For recipes that have already been saved to the database and therefor already have an id
     * @param id
     * @param title
     * @param imagePath
     * @param notes
     */
    public Recipe(long id, String title, String imagePath, String notes)
    {
        this(title, imagePath, notes);
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
