package at.kuchel.kuchelapp.service;

import java.util.List;

import at.kuchel.kuchelapp.RecipeListActivity;
import at.kuchel.kuchelapp.api.Recipe;
import at.kuchel.kuchelapp.builder.RetrofitBuilder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by bernhard on 23.03.2018.
 */

public class RecipeRestService {

    private RecipeListActivity recipeListActivity;

    public RecipeRestService(RecipeListActivity recipeListActivity) {
        this.recipeListActivity = recipeListActivity;
    }

    public void retrieveRecipes() {
        Call<List<Recipe>> call = RetrofitBuilder.createRecipeApi().getRecipes();

        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                List<Recipe> recipes = response.body();
                recipeListActivity.retrievedRecipesFromRest(recipes);

            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                // Log error here since request failed
            }
        });
    }
}
