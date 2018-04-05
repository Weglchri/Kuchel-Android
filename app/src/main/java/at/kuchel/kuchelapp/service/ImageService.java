package at.kuchel.kuchelapp.service;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.util.Date;

import at.kuchel.kuchelapp.api.ImageRequest;
import at.kuchel.kuchelapp.controller.ImageApi;
import at.kuchel.kuchelapp.service.utils.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by bernhard on 03.04.2018.
 */

public class ImageService {

    public void uploadImage(Bitmap bitmap, String recipeId) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();


        ImageRequest imageRequest = new ImageRequest();
        imageRequest.setCreationDate(new Date());
        imageRequest.setRecipeId(Long.parseLong(recipeId));
        imageRequest.setData(byteArray);

        Call<Void> call = ServiceGenerator.createService(ImageApi.class, "bernhard", "pass",false).uploadImage(imageRequest);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                int code = response.code();
                if(code==201){
                    //todo load new from server and store to list activity
                }

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Log error here since request failed
                call.toString();
            }
        });
    }
}
