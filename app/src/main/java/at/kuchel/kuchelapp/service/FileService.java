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

import at.kuchel.kuchelapp.AbstractRecipeActivity;

/**
 * Created by bernhard on 24.03.2018.
 */

public class FileService {

    private static final String IMAGES = "images";
    private static final String JPG = ".jpg";
    private static final String IMAGE = "image";
    private final AbstractRecipeActivity abstractRecipeActivity;

    public FileService(AbstractRecipeActivity abstractRecipeActivity) {
        this.abstractRecipeActivity = abstractRecipeActivity;
    }

    void saveToInternalStorage(Bitmap bitmapImage, String imageId) {
        ContextWrapper cw = new ContextWrapper(abstractRecipeActivity.getApplicationContext());
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
    }

    public Bitmap loadImageFromStorage(String imageId) {
        ContextWrapper cw = new ContextWrapper(abstractRecipeActivity.getApplicationContext());
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
