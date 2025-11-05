package com.example.drewglasser_brown;

/*
 * Name: Drew Glasser-Brown
 * Purpose: This activity will handle taking user input and saving them into the database as a recipe
 */


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddARecipeActivity extends AppCompatActivity {

    private EditText etTitle;
    private EditText etNotes;

    private static int REQUEST_IMAGE_CAPTURE = 1;
    String currentPhotoPath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_arecipe);

        etTitle = findViewById(R.id.etTitle);
        etNotes = findViewById(R.id.etNotes);

    }

    /**
     * This method will pass the view back to the main activity
     * @param v
     */
    public void onCancelPressed(View v)
    {
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
    }

    /**
     * This method will handle when a user presses the use camera button.
     * It just passes to dispatchTakePictureIntent
     * @param v
     */
    public void onUseCameraPressed(View v)
    {
        dispatchTakePictureIntent();
    }

    /**
     * This event handler saves the information in the
     * @param v
     */
    public void onAddRecipePressed(View v)
    {
        if (etTitle.getText().toString().isEmpty() || currentPhotoPath == null || etNotes.getText().toString().isEmpty())
        {
            Toast.makeText(this, "You muse have a Title, Notes, and a photo", Toast.LENGTH_SHORT).show();
            return;
        }
        Recipe recipe = new Recipe(etTitle.getText().toString(), currentPhotoPath, etNotes.getText().toString());
        RecipeDBHelper db = new RecipeDBHelper(this);
        db.open();
        db.createRecipe(recipe);
        db.close();
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
    }


    /**
     * Because we are storing the images in a file in storage we need to make sure there are no duplicate image names.
     * We do that by creating the image name as a timestamp down to the second. It then sets currentPhotoPath in the class so that the photopath can
     * be referenced later when it is saved to the database
     * @return
     * @throws IOException
     */
    private File createImageFile() throws IOException
    {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    /**
     * This method will create ask the camera to take a picture and save it to a file that is
     * created for the photo by calling createImageFile()
     */
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.drewglasser_brown.provider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }
}