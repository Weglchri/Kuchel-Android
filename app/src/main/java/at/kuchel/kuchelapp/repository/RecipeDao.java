package at.kuchel.kuchelapp.repository;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import at.kuchel.kuchelapp.model.IngredientEntity;
import at.kuchel.kuchelapp.model.InstructionEntity;
import at.kuchel.kuchelapp.model.RecipeEntity;
import at.kuchel.kuchelapp.model.RecipeIngredient;
import at.kuchel.kuchelapp.model.RecipeInstruction;

/**
 * Created by bernhard on 19.03.2018.
 */

@Dao
public abstract class RecipeDao {

    public void insertAll(List<RecipeEntity> recipes) {
        for (RecipeEntity recipe : recipes) {
            if (recipe.getInstructions() != null) {
                insertInstructionsForRecipe(recipe, recipe.getInstructions());
            }
            if (recipe.getIngredients() != null) {
                insertIngredientsForRecipe(recipe, recipe.getIngredients());
            }
        }
        _insertAll(recipes);
    }

    public void updateAll(List<RecipeEntity> recipes) {
        for (RecipeEntity recipe : recipes) {
            RecipeInstruction recipeInstruction = findRecipeInstructionByRecipeId(String.valueOf(recipe.getId()));
            for (InstructionEntity oldInstructionEntity : recipeInstruction.instructionEntities) {
                delete(oldInstructionEntity);
            }

            RecipeIngredient recipeIngredients = findRecipeIngredientByRecipeId(String.valueOf(recipe.getId()));
            for (IngredientEntity oldIngredientEntity : recipeIngredients.ingredientEntities) {
                delete(oldIngredientEntity);
            }
            delete(recipe);
        }
        insertAll(recipes);
    }

    private void insertIngredientsForRecipe(RecipeEntity recipeEntity, List<IngredientEntity> instructionEntities) {

        for (IngredientEntity ingredientEntity : instructionEntities) {
            ingredientEntity.setRecipeId(Integer.parseInt(recipeEntity.getId().toString()));
        }
        _insertIngredients(instructionEntities);
    }

    private void insertInstructionsForRecipe(RecipeEntity recipeEntity, List<InstructionEntity> instructionEntities) {

        for (InstructionEntity instructionEntity : instructionEntities) {
            instructionEntity.setRecipeId(Integer.parseInt(recipeEntity.getId().toString()));
        }
        _insertInstructions(instructionEntities);
    }

    public List<RecipeEntity> getRecipesWithInstructionsAndIngredients() {
        List<RecipeInstruction> recipeInstructions = _loadRecipeInstructions();
        List<RecipeIngredient> recipeIngredients = _loadRecipeIngredients();
        List<RecipeEntity> recipes = new ArrayList<>();
        for (RecipeInstruction recipeInstruction : recipeInstructions) {
            recipeInstruction.recipeEntity.setInstructions(recipeInstruction.instructionEntities);
            for (RecipeIngredient recipeIngredient : recipeIngredients) {
                if (Objects.equals(recipeInstruction.recipeEntity.getId(), recipeIngredient.recipeEntity.getId())) {
                    recipeInstruction.recipeEntity.setIngredients(recipeIngredient.ingredientEntities);
                }
            }
            recipes.add(recipeInstruction.recipeEntity);
        }
        return recipes;
    }


    @Query("SELECT * FROM RecipeEntity")
    abstract List<RecipeInstruction> _loadRecipeInstructions();

    @Query("SELECT * FROM RecipeEntity")
    abstract List<RecipeIngredient> _loadRecipeIngredients();

    @Query("SELECT * FROM RecipeEntity WHERE name LIKE :name LIMIT 1")
    abstract RecipeEntity findByName(String name);

    @Query("SELECT * FROM RecipeEntity WHERE id = :id ")
    public abstract RecipeEntity findById(String id);

    @Query("SELECT * FROM RecipeEntity WHERE id = :id ")
    public abstract RecipeInstruction findRecipeInstructionByRecipeId(String id);

    @Query("SELECT * FROM RecipeEntity WHERE id = :id ")
    public abstract RecipeIngredient findRecipeIngredientByRecipeId(String id);

    @Query("SELECT id FROM RecipeEntity")
    public abstract List<Long> getApiIds();

    @Insert
    abstract void _insertInstructions(List<InstructionEntity> instructionEntities);

    @Insert
    abstract void _insertIngredients(List<IngredientEntity> ingredientEntities);

    @Insert
    abstract void _insertAll(List<RecipeEntity> recipeEntities);

    @Update
    abstract void _updateAll(List<RecipeEntity> recipeEntities);

    @Update
    abstract void update(RecipeEntity recipeEntity);

    @Update
    abstract void update(InstructionEntity instructionEntity);

    @Update
    abstract void update(IngredientEntity ingredientEntity);

    @Delete
    abstract void delete(RecipeEntity recipeEntity);

    @Delete
    abstract void delete(InstructionEntity instructionEntity);

    @Delete
    abstract void delete(IngredientEntity ingredientEntity);
}

