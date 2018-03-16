package at.kuchel.kuchelapp.builder;


import at.kuchel.kuchelapp.api.RecipeApiAsync;
import at.kuchel.kuchelapp.api.RecipeApiSync;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


import static at.kuchel.kuchelapp.Constant.BASE_URL;

/**
 * Created by bernhard on 15.03.2018.
 */

public class RetrofitBuilder {

    public static RecipeApiAsync createRecipeApiAsync() {
        return createRetrofit().create(RecipeApiAsync.class);
    }

    public static RecipeApiSync createRecipeApiSync() {
        return createRetrofit().create(RecipeApiSync.class);
    }

    private static Retrofit createRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
