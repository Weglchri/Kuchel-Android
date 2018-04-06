package at.kuchel.kuchelapp.service;

import android.util.Log;

import org.springframework.http.HttpStatus;

import at.kuchel.kuchelapp.api.Profile;
import at.kuchel.kuchelapp.builder.GlobalParamBuilder;
import at.kuchel.kuchelapp.controller.ProfileApi;
import at.kuchel.kuchelapp.service.utils.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static at.kuchel.kuchelapp.Constants.GLOBAL_PARAM.PASSWORD;
import static at.kuchel.kuchelapp.Constants.GLOBAL_PARAM.USERNAME;


public class UserService {

    public void loadUserProfileViaRest(final String username, final String password) {
        Call<Profile> call = ServiceGenerator.createService(ProfileApi.class, username, password,true).getProfile();

        call.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                int code = response.code();
                if (code == HttpStatus.OK.value()) {

                    Profile profile = response.body();

                    //username and password id correct and can be stored
                    GlobalParamService.storeGlobalParam(new GlobalParamBuilder()
                            .setKey(USERNAME).setValue(username).build());
                    GlobalParamService.storeGlobalParam(new GlobalParamBuilder()
                            .setKey(PASSWORD).setValue(password).build());

                    Log.i("UserService ", "Correct Credentials");
                    System.out.println("Profile: " + profile);

                } else {
                    Log.i("loaduserProfileViaRest", "Incorrect Credentials");
                    //todo return to activity with error

                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                // Log error here since request failed
                Log.i("UserService ", "Wrong Credentials");
            }
        });
    }
}
