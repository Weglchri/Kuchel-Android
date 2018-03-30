package at.kuchel.kuchelapp.controller;


import at.kuchel.kuchelapp.api.Profile;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by bernhard on 14.03.2018.
 */

public interface ProfileApi {

    @GET("profiles")
    Call<Profile> getProfile();

}

