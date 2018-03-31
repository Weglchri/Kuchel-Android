package at.kuchel.kuchelapp.service;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import at.kuchel.kuchelapp.builder.GlobalParamBuilder;
import at.kuchel.kuchelapp.model.GlobalParamEntity;

/**
 * Created by bernhard on 19.03.2018.
 */


public class GlobalParamService {

    @SuppressLint("StaticFieldLeak")
    public static void storeGlobalParam(final String key, final String value) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                DatabaseManager.getDatabase().globalParamDao().insert(new GlobalParamBuilder().setKey(key).setValue(value).build());
                return null;
            }
        }.execute();
    }


    public static GlobalParamEntity retrieveGlobalParam(String key) {
        return DatabaseManager.getDatabaseReadOnly().globalParamDao().findByKey(key);
    }
}

