package at.kuchel.kuchelapp.service;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import at.kuchel.kuchelapp.AbstractRecipeActivity;
import at.kuchel.kuchelapp.api.Image;
import at.kuchel.kuchelapp.api.Recipe;
import at.kuchel.kuchelapp.builder.GlobalParamBuilder;
import at.kuchel.kuchelapp.controller.ImageApi;
import at.kuchel.kuchelapp.controller.RecipeApi;
import at.kuchel.kuchelapp.dto.BitmapImage;
import at.kuchel.kuchelapp.mapper.LastSyncMapper;
import at.kuchel.kuchelapp.model.GlobalParamEntity;
import at.kuchel.kuchelapp.service.utils.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static at.kuchel.kuchelapp.Constants.GLOBAL_PARAM.LAST_SYNC_DATE;

/**
 * Created by bernhard on 23.03.2018.
 */
public class RecipeServiceRest { //implements Observable

    private AbstractRecipeActivity abstractRecipeActivity;
    private FileService fileService;

    public RecipeServiceRest(AbstractRecipeActivity abstractRecipeActivity) {
        this.abstractRecipeActivity = abstractRecipeActivity;
        fileService = new FileService(abstractRecipeActivity);
    }

    public void retrieveRecipes() {

        GlobalParamEntity lastSyncDate = GlobalParamService.retrieveGlobalParam(LAST_SYNC_DATE);
        Call<List<Recipe>> call;
        if (lastSyncDate != null) {
            call = ServiceGenerator.createService(RecipeApi.class, true).getRecipes(LastSyncMapper.map(lastSyncDate));
        } else {
            call = ServiceGenerator.createService(RecipeApi.class, true).getRecipes();
        }


        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                List<Recipe> recipes = response.body();
                abstractRecipeActivity.handleRecipesFromRest(recipes);
                GlobalParamService.storeGlobalParam(new GlobalParamBuilder().setKey(LAST_SYNC_DATE)
                        .setValue(String.valueOf(new Date().getTime())).build());
                for (Recipe recipe : recipes) {
                    if (recipe.getImages().size() > 0) {

                        //todo check if update is needed
                        retrieveImagesFromRestAndStoreToFileSystem(recipe.getId(), recipe.getImages().get(0).getId());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.i("no_connection", "Could not establish connection - try to load from db");
                abstractRecipeActivity.handleRecipesFromRest(Collections.<Recipe>emptyList());
            }
        });
    }

    private void retrieveImagesFromRestAndStoreToFileSystem(Long recipeId, String imageId) {
        Call<Image> call = ServiceGenerator.createService(ImageApi.class, false).getImage(imageId);

        call.enqueue(new Callback<Image>() {
            @Override
            public void onResponse(Call<Image> call, Response<Image> response) {
                Image image = response.body();

                if (image != null) {
                    // 1. create bitmap from response image
                    Bitmap bitmap = getBitmap(image);
                    if (bitmap != null) {

                        // 2. store bitmap as file to storage
                        fileService.saveToInternalStorage(bitmap, image.getId());

                        // 3. return bitmap with imageId to refresh the right element
                        abstractRecipeActivity.handleImageResponse(new BitmapImage(image.getId(), bitmap));
                    }
                } else {
                    abstractRecipeActivity.handleImageResponse(null);
                }
            }

            @Override
            public void onFailure(Call<Image> call, Throwable t) {
                // Log error here since request failed
                call.toString();
            }
        });
    }

    private Bitmap getBitmap(Image image) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inDither = true;
        opt.inPreferredConfig = Bitmap.Config.ARGB_8888;
        byte[] imageByteArray = image.getData();
        return BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length, opt);
    }
}
