package at.kuchel.kuchelapp.controller;


import at.kuchel.kuchelapp.api.RecipeResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by bernhard on 14.03.2018.
 */

public interface ProfileApi {

    @GET("profile/{username}")
    Call<RecipeResponse> getProfile(@Path("username") String username);

}

