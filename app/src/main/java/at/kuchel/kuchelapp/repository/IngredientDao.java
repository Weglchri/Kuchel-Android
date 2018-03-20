package at.kuchel.kuchelapp.repository;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import at.kuchel.kuchelapp.model.Ingredient;
import at.kuchel.kuchelapp.model.Instruction;

/**
 * Created by bernhard on 19.03.2018.
 */

@Dao
public interface IngredientDao {

    @Query("SELECT * FROM ingredient")
    List<Ingredient> getAll();

    @Insert
    void insertAll(List<Ingredient> ingredient);

    @Update
    void update(Ingredient ingredient);

    @Delete
    void delete(Ingredient ingredient);
}

