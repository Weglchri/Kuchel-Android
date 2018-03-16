package at.kuchel.kuchelapp.api;

import java.util.List;

import at.kuchel.kuchelapp.model.Recipe;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by bernhard on 14.03.2018.
 */

public interface RecipeApiSync {

    @GET("recipes/{id}")
    Call<Recipe> getRecipe(@Path("id") String id);

    @GET("recipes")
    Call<List<Recipe>> getRecipes();

}

