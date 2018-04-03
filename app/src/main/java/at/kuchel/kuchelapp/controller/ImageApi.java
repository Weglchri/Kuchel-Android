package at.kuchel.kuchelapp.controller;

import at.kuchel.kuchelapp.api.Image;
import at.kuchel.kuchelapp.api.ImageRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by bernhard on 14.03.2018.
 */

public interface ImageApi {

    @GET("recipes/{recipeId}/images/{imageId}")
    Call<Image> getImage(@Path("recipeId") String recipeId, @Path("imageId") String imageId);

    @PUT("images")
    Call<Void> uploadImage(@Body ImageRequest imageRequest);

}

