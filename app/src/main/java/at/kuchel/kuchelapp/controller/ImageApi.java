package at.kuchel.kuchelapp.controller;

import org.springframework.http.MediaType;

import java.util.List;

import at.kuchel.kuchelapp.api.Image;
import at.kuchel.kuchelapp.api.Recipe;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by bernhard on 14.03.2018.
 */

public interface ImageApi {

    @GET("recipes/{recipeId}/images/{imageId}")
    Call<Image> getImage(@Path("recipeId") String recipeId, @Path("imageId") String imageId);

}

