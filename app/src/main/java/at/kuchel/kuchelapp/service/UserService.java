package at.kuchel.kuchelapp.service;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import at.kuchel.kuchelapp.api.Profile;
import at.kuchel.kuchelapp.builder.GlobalParamBuilder;
import at.kuchel.kuchelapp.controller.ProfileApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static at.kuchel.kuchelapp.Constants.GLOBAL_PARAM.PASSWORD;
import static at.kuchel.kuchelapp.Constants.GLOBAL_PARAM.USERNAME;

/**
 * Created by bernhard on 30.03.2018.
 */

public class UserService {

    public void loadUserProfileViaRest(final String username, final String password) {
        Call<Profile> call = ServiceGenerator.createService(ProfileApi.class, username, password).getProfile();

        call.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                int code = response.code();
                if (code == 200) {
                    //todo return to activity
                    Profile profile = response.body();
                    //todo store to db


                    storeGlobalParams(USERNAME, username);
                    storeGlobalParams(PASSWORD, password);

                } else {
                    //todo return to activity
                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                // Log error here since request failed
            }
        });
    }

    //todo extract to own class
    @SuppressLint("StaticFieldLeak")
    private void storeGlobalParams(final String key, final String value) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                DatabaseManager.getDatabase().globalParamDao().insert(new GlobalParamBuilder().setKey(key).setValue(value).build());
                return null;
            }
        }.execute();
    }

}
