package at.kuchel.kuchelapp.service;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import at.kuchel.kuchelapp.model.GlobalParamEntity;
import at.kuchel.kuchelapp.service.utils.DatabaseManager;

import static at.kuchel.kuchelapp.Constants.GLOBAL_PARAM.PASSWORD;
import static at.kuchel.kuchelapp.Constants.GLOBAL_PARAM.USERNAME;

/**
 * Created by bernhard on 19.03.2018.
 */


public class GlobalParamService {

    @SuppressLint("StaticFieldLeak")
    public static void storeGlobalParam(final GlobalParamEntity globalParam) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {

                GlobalParamEntity gp = DatabaseManager.getDatabaseReadOnly().globalParamDao().findByKey(globalParam.getKey());
                if (gp == null) {
                    DatabaseManager.getDatabase().globalParamDao().insert(globalParam);
                } else {
                    DatabaseManager.getDatabase().globalParamDao().update(globalParam);
                }

                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        }.execute();
    }


    public static GlobalParamEntity retrieveGlobalParam(String key) {
        return DatabaseManager.getDatabaseReadOnly().globalParamDao().findByKey(key);
    }

    public static boolean isUserSet() {
        return DatabaseManager.getDatabaseReadOnly().globalParamDao().findByKey(USERNAME) != null;
    }


    public static void clearUserdata() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                DatabaseManager.getDatabase().globalParamDao().deleteByKey(USERNAME);
                DatabaseManager.getDatabase().globalParamDao().deleteByKey(PASSWORD);
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        }.execute();
    }

}

