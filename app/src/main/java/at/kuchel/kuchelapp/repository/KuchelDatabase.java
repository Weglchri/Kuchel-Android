package at.kuchel.kuchelapp.repository;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import at.kuchel.kuchelapp.model.IngredientEntity;
import at.kuchel.kuchelapp.model.InstructionEntity;
import at.kuchel.kuchelapp.model.RecipeEntity;


/**
 * Created by bernhard on 19.03.2018.
 */

@Database(entities = {RecipeEntity.class, InstructionEntity.class, IngredientEntity.class}, version = 1, exportSchema = false)
public abstract class KuchelDatabase extends RoomDatabase {
    public abstract RecipeDao recipeDao();
    public abstract InstructionDao instructionDao();
    public abstract IngredientDao ingredientDao();
}

