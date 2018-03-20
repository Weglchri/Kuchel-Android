package at.kuchel.kuchelapp.repository;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import at.kuchel.kuchelapp.model.RecipeEntity;

/**
 * Created by bernhard on 19.03.2018.
 */

@Dao
public interface RecipeDao {

    @Query("SELECT * FROM RecipeEntity")
    List<RecipeEntity> getAll();

    @Query("SELECT * FROM RecipeEntity WHERE name LIKE :name LIMIT 1")
    RecipeEntity findByName(String name);

    @Insert
    void insertAll(List<RecipeEntity> recipeEntities);

    @Update
    void update(RecipeEntity recipeEntity);

    @Delete
    void delete(RecipeEntity recipeEntity);
}

