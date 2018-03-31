package at.kuchel.kuchelapp.service;

import at.kuchel.kuchelapp.api.Profile;
import at.kuchel.kuchelapp.controller.ProfileApi;
import at.kuchel.kuchelapp.model.GlobalParamEntity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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


//                    GlobalParamService.storeGlobalParam(USERNAME, username);
//                    GlobalParamService.storeGlobalParam(PASSWORD, password);

                    GlobalParamEntity entity = GlobalParamService.retrieveGlobalParam(USERNAME);

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
}
