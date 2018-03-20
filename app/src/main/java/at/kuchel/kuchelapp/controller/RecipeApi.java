package at.kuchel.kuchelapp.controller;

import java.util.List;


import at.kuchel.kuchelapp.api.RecipeResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by bernhard on 14.03.2018.
 */

public interface RecipeApi {

    @GET("recipes/{id}")
    Call<RecipeResponse> getRecipe(@Path("id") String id);

    @GET("recipes")
    Call<List<RecipeResponse>> getRecipes();

}

