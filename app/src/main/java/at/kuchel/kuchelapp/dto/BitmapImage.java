package at.kuchel.kuchelapp.dto;

import android.graphics.Bitmap;

import lombok.Data;

/**
 * Created by bernhard on 25.03.2018.
 */
@Data
public class BitmapImage {

    private String imageId;
    private Bitmap image;


    public BitmapImage(String imageId, Bitmap image) {
        this.imageId = imageId;
        this.image = image;
    }
}
