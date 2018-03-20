package at.kuchel.kuchelapp.repository;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import at.kuchel.kuchelapp.model.Ingredient;
import at.kuchel.kuchelapp.model.Instruction;
import at.kuchel.kuchelapp.model.Recipe;
import at.kuchel.kuchelapp.model.RecipeInstruction;


/**
 * Created by bernhard on 19.03.2018.
 */

@Database(entities = {Recipe.class, Instruction.class, Ingredient.class}, version = 1, exportSchema = false)
public abstract class KuchelDatabase extends RoomDatabase {
    public abstract RecipeDao recipeDao();
    public abstract InstructionDao instructionDao();
    public abstract IngredientDao ingredientDao();
}

