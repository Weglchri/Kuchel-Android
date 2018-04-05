package at.kuchel.kuchelapp.service;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import at.kuchel.kuchelapp.model.GlobalParamEntity;
import at.kuchel.kuchelapp.service.utils.DatabaseManager;

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
                if(gp==null){
                    DatabaseManager.getDatabase().globalParamDao().insert(globalParam);
                }else{
                    DatabaseManager.getDatabase().globalParamDao().update(globalParam);
                }
                return null;
            }
        }.execute();
    }


    public static GlobalParamEntity retrieveGlobalParam(String key) {
        return DatabaseManager.getDatabaseReadOnly().globalParamDao().findByKey(key);
    }
}

