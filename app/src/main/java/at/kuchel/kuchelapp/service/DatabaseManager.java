package at.kuchel.kuchelapp.service;

import android.arch.persistence.room.Room;
import android.content.Context;

import at.kuchel.kuchelapp.repository.KuchelDatabase;

import static at.kuchel.kuchelapp.RecipeListActivity.KUCHEL;

/**
 * Created by bernhard on 31.03.2018.
 */

public class DatabaseManager {

    private static DatabaseManager mInstance;
    private static KuchelDatabase kuchel;
    private static KuchelDatabase kuchelReadOnly;

    public DatabaseManager(Context context) {
        kuchel = Room.databaseBuilder(context, KuchelDatabase.class, KUCHEL).build();
        kuchelReadOnly = Room.databaseBuilder(context, KuchelDatabase.class, KUCHEL).allowMainThreadQueries().build();
    }

    public static KuchelDatabase getDatabase() {
        return kuchel;
    }
    public static KuchelDatabase getDatabaseReadOnly() {
        return kuchelReadOnly;
    }

    public static KuchelDatabase getDatabase(Context context) {
        if (mInstance == null) {
            mInstance = new DatabaseManager(context);
        }
        return kuchel;
    }
}
