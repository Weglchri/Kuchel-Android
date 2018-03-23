package at.kuchel.kuchelapp.service;

import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.List;

import at.kuchel.kuchelapp.RecipeListActivity;
import at.kuchel.kuchelapp.api.Recipe;
import at.kuchel.kuchelapp.repository.KuchelDatabase;

/**
 * Created by bernhard on 23.03.2018.
 */

public class DatabaseManager {

    public static final String KUCHEL = "kuchel";
    private KuchelDatabase database;
    private RecipeService recipeService = new RecipeService();

    private void createDatabase(Context context) {
        database = Room.databaseBuilder(context, KuchelDatabase.class, KUCHEL).build();
    }

    public void storeAndUpdateRecipes(final List<Recipe> recipes, Context context) {
        if (database == null) {
            createDatabase(context);
        }
        recipeService.storeNewAndUpdateExistingRecipes(recipes, database);
    }

    public void loadRecipes(Context context, RecipeListActivity recipeListActivity) {
        if (database == null) {
            createDatabase(context);
        }
        recipeService.retrieveRecipes(database,recipeListActivity);
    }
}
