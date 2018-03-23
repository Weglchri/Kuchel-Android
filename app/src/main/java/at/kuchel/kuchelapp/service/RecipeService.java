package at.kuchel.kuchelapp.service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import at.kuchel.kuchelapp.RecipeListActivity;
import at.kuchel.kuchelapp.api.Recipe;
import at.kuchel.kuchelapp.mapper.RecipeMapper;
import at.kuchel.kuchelapp.model.RecipeEntity;
import at.kuchel.kuchelapp.repository.KuchelDatabase;

/**
 * Created by bernhard on 23.03.2018.
 */

public class RecipeService {

    private DatabaseManager databaseManager;

    @SuppressLint("StaticFieldLeak")
    public void storeNewAndUpdateExistingRecipes(final List<Recipe> recipes, final KuchelDatabase database) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                final List<Recipe> mustUpdate = new ArrayList<>();
                final List<Recipe> mustCreate = new ArrayList<>();

                List<Long> apiIds = database.recipeDao().getApiIds();

                for (Recipe recipe : recipes) {
                    if (!apiIds.contains(recipe.getId())) {
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
    private void updateRecipes(final List<Recipe> recipes, final KuchelDatabase database) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                database.recipeDao().updateAll(RecipeMapper.mapToEntity(recipes));
                return null;
            }

        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    public void retrieveRecipes(final KuchelDatabase database, final RecipeListActivity recipeListActivity) {
        new AsyncTask<Void, Void, Void>() {
            private List<Recipe> recipes = new ArrayList<>();

            @Override
            protected Void doInBackground(Void... params) {
                recipes.addAll(RecipeMapper.mapToApi(database.recipeDao().getRecipesWithInstructionsAndIngredients()));
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                recipeListActivity.handleReturnFromDb(recipes);
            }
        }.execute();
    }
}
