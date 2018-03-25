package at.kuchel.kuchelapp.builder;


import at.kuchel.kuchelapp.controller.ImageApi;
import at.kuchel.kuchelapp.controller.RecipeApi;

import at.kuchel.kuchelapp.service.GsonHelper;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


import static at.kuchel.kuchelapp.Constant.BASE_URL;

/**
 * Created by bernhard on 15.03.2018.
 */

public class RetrofitBuilder {

    public static RecipeApi createRecipeApi() {
        return createRetrofit().create(RecipeApi.class);
    }

    public static ImageApi createImageApi() {
        return createRetrofitWithOwnGson().create(ImageApi.class);
    }

    private static Retrofit createRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private static Retrofit createRetrofitWithOwnGson() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(GsonHelper.customGson))
                .build();
    }
}
