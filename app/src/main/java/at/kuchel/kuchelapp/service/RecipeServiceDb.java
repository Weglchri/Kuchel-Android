package at.kuchel.kuchelapp.service;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import at.kuchel.kuchelapp.RecipeDetailFragment;
import at.kuchel.kuchelapp.RecipeListActivity;
import at.kuchel.kuchelapp.api.Recipe;
import at.kuchel.kuchelapp.dto.BitmapImage;
import at.kuchel.kuchelapp.mapper.RecipeMapper;
import at.kuchel.kuchelapp.repository.KuchelDatabase;
import at.kuchel.kuchelapp.service.utils.DatabaseManager;

/**
 * Created by bernhard on 23.03.2018.
 */

public class RecipeServiceDb {

    private RecipeListActivity recipeListActivity;
    private RecipeDetailFragment recipeDetailFragment;
    private FileService fileService;

    public RecipeServiceDb(RecipeListActivity recipeListActivity) {
        this.recipeListActivity = recipeListActivity;
        this.fileService = new FileService(recipeListActivity);
    }

    public RecipeServiceDb(RecipeDetailFragment recipeDetailFragment) {
        this.recipeDetailFragment = recipeDetailFragment;
        this.fileService = new FileService(recipeListActivity);
    }

    @SuppressLint("StaticFieldLeak")
    public void storeNewAndUpdateExistingRecipes(final List<Recipe> recipes, final KuchelDatabase database) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                final List<Recipe> mustUpdate = new ArrayList<>();
                final List<Recipe> mustCreate = new ArrayList<>();

                List<Long> recipeIds = database.recipeDao().getApiIds();

                for (Recipe recipe : recipes) {
                    if (!recipeIds.contains(recipe.getId())) {
                        mustCreate.add(recipe);
                    } else if (recipe.getModifiedDate().after(database.recipeDao().findById(String.valueOf(recipe.getId())).getModifiedDate())) {
                        mustUpdate.add(recipe);
                    } else {
                        Log.i("no_update", "no update needed");
                    }
                }
                storeRecipes(mustCreate, database);
                updateRecipes(mustUpdate, database);
                return null;
            }

        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    public void retrieveRecipes() {
        new AsyncTask<Void, Void, Void>() {
            private List<Recipe> recipes = new ArrayList<>();

            @Override
            protected Void doInBackground(Void... params) {
                recipes.addAll(RecipeMapper.mapToApi(DatabaseManager.getDatabase().recipeDao().getRecipesWithInstructionsAndIngredients()));
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if(recipeListActivity==null && recipeDetailFragment!=null){
                    recipeDetailFragment.handleRetrievedRecipesFromDb(recipes);
                }else{
                    recipeListActivity.retrievedRecipesFromDatabase(recipes);

                }
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    private void storeRecipes(final List<Recipe> recipes, final KuchelDatabase database) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                database.recipeDao().insertAll(RecipeMapper.mapToEntity(recipes));
                return null;
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    public void loadImages(final String imageId) {
        new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... params) {
                return fileService.loadImageFromStorage(imageId);
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                recipeListActivity.retrievedImageBitmap(new BitmapImage(imageId, bitmap));
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    private void updateRecipes(final List<Recipe> recipes, final KuchelDatabase database) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                database.recipeDao().updateAll(RecipeMapper.mapToEntity(recipes));
                return null;
            }

        }.execute();
    }
}
