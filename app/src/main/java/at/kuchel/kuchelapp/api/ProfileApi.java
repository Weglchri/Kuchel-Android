package at.kuchel.kuchelapp.api;


import at.kuchel.kuchelapp.model.Recipe;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by bernhard on 14.03.2018.
 */

public interface ProfileApi {

    @GET("profile/{username}")
    Call<Recipe> getProfile(@Path("username") String username);

}

