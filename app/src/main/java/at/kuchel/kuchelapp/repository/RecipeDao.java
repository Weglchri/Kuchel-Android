package at.kuchel.kuchelapp.repository;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import at.kuchel.kuchelapp.model.Recipe;

/**
 * Created by bernhard on 19.03.2018.
 */

@Dao
public interface RecipeDao {

    @Query("SELECT * FROM recipe")
    List<Recipe> getAll();

    @Query("SELECT * FROM recipe WHERE name LIKE :name LIMIT 1")
    Recipe findByName(String name);

    @Insert
    void insertAll(List<Recipe> recipes);

    @Update
    void update(Recipe recipe);

    @Delete
    void delete(Recipe recipe);
}

