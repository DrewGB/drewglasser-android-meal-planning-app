package com.example.drewglasser_brown;

/*
 * Name: Drew Glasser-Brown
 * Purpose: This activity is responsible for displaying the recipe that is passed to it for the user to view
 */

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ViewRecipeActivity extends AppCompatActivity {

    private TextView txtTitle;
    private TextView txtNotes;
    private ImageView ivPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);

        txtTitle = findViewById(R.id.txtTitle);
        txtNotes = findViewById(R.id.txtNotes);
        ivPhoto = findViewById(R.id.ivRecipePhoto);

        //Grab the recipe from the intent. We had to make recipe serializable to do this
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        Recipe recipe = (Recipe) b.get("recipe");

        //Prevent null pointers
        if (recipe != null)
        {
            //Set the data
            txtTitle.setText(recipe.getTitle());
            txtNotes.setText(recipe.getNotes());
            //Read the image from the file into a bitmap
            Bitmap bitmap = BitmapFactory.decodeFile(recipe.getImagePath());
            //Set the photo in the imageview
            ivPhoto.setImageBitmap(bitmap);
        }
    }

    /**
     * This method will return to the Main activity
     * @param v
     */
    public void onGoBackPressed(View v)
    {
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
    }
}