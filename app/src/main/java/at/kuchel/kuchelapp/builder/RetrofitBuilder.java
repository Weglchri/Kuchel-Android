package at.kuchel.kuchelapp.builder;


import at.kuchel.kuchelapp.controller.RecipeApi;
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

    private static Retrofit createRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
