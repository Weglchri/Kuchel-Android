package at.kuchel.kuchelapp.controller;

import java.util.List;

import at.kuchel.kuchelapp.api.LastSyncRequest;
import at.kuchel.kuchelapp.api.Recipe;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by bernhard on 14.03.2018.
 */

public interface RecipeApi {

    @GET("recipes/{id}")
    Call<Recipe> getRecipe(@Path("id") String id);

    @GET("recipes")
    Call<List<Recipe>> getRecipes();

    @POST("recipes")
    Call<List<Recipe>> getRecipes(@Body LastSyncRequest lastSyncRequest);

}

