package at.kuchel.kuchelapp.service;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import at.kuchel.kuchelapp.RecipeListActivity;

/**
 * Created by bernhard on 24.03.2018.
 */

public class FileService {

    public static final String IMAGES = "images";
    public static final String JPG = ".jpg";
    private static final String IMAGE = "image";
    private final RecipeListActivity recipeListActivity;

    public FileService(RecipeListActivity recipeListActivity) {
        this.recipeListActivity = recipeListActivity;
    }

    public String saveToInternalStorage(Bitmap bitmapImage, String imageId) {
        ContextWrapper cw = new ContextWrapper(recipeListActivity.getApplicationContext());
        File directory = cw.getDir(IMAGES, Context.MODE_PRIVATE);
        File mypath = new File(directory, IMAGE + "_" + imageId + JPG);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            //compress if needed
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

    public Bitmap loadImageFromStorage(String imageId) {
        ContextWrapper cw = new ContextWrapper(recipeListActivity.getApplicationContext());
        File directory = cw.getDir(IMAGES, Context.MODE_PRIVATE);

        try {
            File f = new File(directory, IMAGE + "_" + imageId + JPG);
            return BitmapFactory.decodeStream(new FileInputStream(f));


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
