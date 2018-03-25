package at.kuchel.kuchelapp.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;

import at.kuchel.kuchelapp.RecipeListActivity;
import at.kuchel.kuchelapp.api.Image;
import at.kuchel.kuchelapp.api.Recipe;
import at.kuchel.kuchelapp.builder.RetrofitBuilder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by bernhard on 23.03.2018.
 */

public class RecipeServiceRest {

    private RecipeListActivity recipeListActivity;
    private FileService fileService;

    public RecipeServiceRest(RecipeListActivity recipeListActivity) {
        this.recipeListActivity = recipeListActivity;
        fileService = new FileService(recipeListActivity);
    }

    public void retrieveRecipes() {
        Call<List<Recipe>> call = RetrofitBuilder.createRecipeApi().getRecipes();

        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                List<Recipe> recipes = response.body();
                recipeListActivity.retrievedRecipesFromRest(recipes);

                for (Recipe recipe : recipes) {
                    if (recipe.getImages().size() > 0) {
                        retrieveImages(recipe.getId(), recipe.getImages().get(0).getId());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                // Log error here since request failed
            }
        });
    }

    private void retrieveImages(Long recipeId, String imageId) {
        Call<Image> call = RetrofitBuilder.createImageApi().getImage(String.valueOf(recipeId), imageId);

        call.enqueue(new Callback<Image>() {
            @Override
            public void onResponse(Call<Image> call, Response<Image> response) {
                Image image = response.body();

                if (image != null) {

                    // 1. create bitmap from response image
                    BitmapFactory.Options opt = new BitmapFactory.Options();
                    opt.inDither = true;
                    opt.inPreferredConfig = Bitmap.Config.ARGB_8888;
                    byte[] imageByteArray = image.getData();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length, opt);
                    if (bitmap != null) {

                        // 2. store bitmap as file to storage
                        fileService.saveToInternalStorage(bitmap, image.getId());

                        recipeListActivity.retrievedImageBitmapsFromDatabase(bitmap);
                    }
                }
            }

            @Override
            public void onFailure(Call<Image> call, Throwable t) {
                // Log error here since request failed
                call.toString();
            }
        });
    }
}
