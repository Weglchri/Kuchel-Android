package at.kuchel.kuchelapp.service;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;

import java.io.ByteArrayOutputStream;
import java.util.Date;

import at.kuchel.kuchelapp.RecipeDetailActivity;
import at.kuchel.kuchelapp.api.ImageRequest;
import at.kuchel.kuchelapp.controller.ImageApi;
import at.kuchel.kuchelapp.service.utils.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static at.kuchel.kuchelapp.Constants.GLOBAL_PARAM.PASSWORD;
import static at.kuchel.kuchelapp.Constants.GLOBAL_PARAM.USERNAME;

/**
 * Created by bernhard on 03.04.2018.
 */

public class ImageService {

    public void uploadImage(Bitmap bitmap, String recipeId, RecipeDetailActivity recipeDetailActivity) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();


        ImageRequest imageRequest = buildImageRequest(recipeId, byteArray);

        Call<Void> call = ServiceGenerator.createService(ImageApi.class,
                GlobalParamService.retrieveGlobalParam(USERNAME).getValue(),
                GlobalParamService.retrieveGlobalParam(PASSWORD).getValue())
                .uploadImage(imageRequest);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                int code = response.code();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Log error here since request failed
//                Snackbar.make(recipeDetailActivity.getv, "Hochladen des Bildes nicht erfolgreich", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
    }

    @NonNull
    private ImageRequest buildImageRequest(String recipeId, byte[] byteArray) {
        ImageRequest imageRequest = new ImageRequest();
        imageRequest.setCreationDate(new Date());
        imageRequest.setRecipeId(Long.parseLong(recipeId));
        imageRequest.setData(byteArray);
        return imageRequest;
    }
}
