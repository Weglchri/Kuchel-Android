package at.kuchel.kuchelapp.service;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.springframework.http.HttpStatus;

import at.kuchel.kuchelapp.AbstractActivity;
import at.kuchel.kuchelapp.AbstractRecipeActivity;
import at.kuchel.kuchelapp.MainActivity;
import at.kuchel.kuchelapp.R;
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

    public void loadUserProfileViaRest(final AbstractActivity abstractActivity, final String username, final String password) {

        Call<Profile> call = ServiceGenerator.createService(ProfileApi.class, username, password).getProfile();

        call.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                int code = response.code();
                if (code == HttpStatus.OK.value()) {

                    Profile profile = response.body(); //maybe can be used in later features

                    GlobalParamService.storeGlobalParam(new GlobalParamBuilder()
                            .setKey(USERNAME).setValue(username).build());
                    GlobalParamService.storeGlobalParam(new GlobalParamBuilder()
                            .setKey(PASSWORD).setValue(password).build());

                    Log.i("UserService ", "Correct Credentials");
                    abstractActivity.callSnackBarPopup("Erfolgreich eingeloggt");

                } else {
                    Log.i("loaduserProfileViaRest", "Incorrect Credentials");
                    abstractActivity.callSnackBarPopup("Falsche Zugangsdaten");
                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                // Log error here since request failed
                abstractActivity.callSnackBarPopup("Keine Verbindung zum Server möglich");
                Log.i("UserService ", "Wrong Credentials");
            }
        });
    }
}
