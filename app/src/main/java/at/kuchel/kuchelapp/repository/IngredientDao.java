package at.kuchel.kuchelapp.repository;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import at.kuchel.kuchelapp.model.IngredientEntity;

/**
 * Created by bernhard on 19.03.2018.
 */

@Dao
public interface IngredientDao {

    @Query("SELECT * FROM IngredientEntity")
    List<IngredientEntity> getAll();

    @Insert
    void insertAll(List<IngredientEntity> ingredientEntity);

    @Update
    void update(IngredientEntity ingredientEntity);

    @Delete
    void delete(IngredientEntity ingredientEntity);
}

