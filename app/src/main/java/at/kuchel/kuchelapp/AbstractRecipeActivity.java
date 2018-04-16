package at.kuchel.kuchelapp;

import android.support.v7.app.AppCompatActivity;

import java.util.List;

import at.kuchel.kuchelapp.api.Recipe;
import at.kuchel.kuchelapp.dto.BitmapImage;

/**
 * Created by bernhard on 06.04.2018.
 */

public abstract class AbstractRecipeActivity extends AppCompatActivity {

    public abstract void handleRecipesFromRest(List<Recipe> recipes);
    public abstract void handleRecipesFromDb(List<Recipe> recipes);
    public abstract void handleImageResponse(BitmapImage bitmapImage);
    public abstract void callSnackBarPopup(String message);
}
