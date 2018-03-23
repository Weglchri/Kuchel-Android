package at.kuchel.kuchelapp.mapper;

import java.util.ArrayList;
import java.util.List;

import at.kuchel.kuchelapp.api.Recipe;
import at.kuchel.kuchelapp.model.RecipeEntity;

/**
 * Created by bernhard on 19.03.2018.
 */

public class RecipeMapper {

    public static List<RecipeEntity> mapToEntity(List<Recipe> recipes) {
        List<RecipeEntity> recipeEntities = new ArrayList<>();
        for (Recipe recipe : recipes) {
            recipeEntities.add(mapToEntity(recipe));
        }
        return recipeEntities;
    }

    public static List<Recipe> mapToApi(List<RecipeEntity> recipeEntities) {
        List<Recipe> recipes = new ArrayList<>();
        for (RecipeEntity recipeEntity : recipeEntities) {
            recipes.add(mapToEntity(recipeEntity));
        }
        return recipes;
    }

    private static Recipe mapToEntity(RecipeEntity recipeEntity) {
        Recipe recipe = new Recipe();
        recipe.setDifficulty(recipeEntity.getDifficulty());
        recipe.setId(recipeEntity.getApiId());
        recipe.setName(recipeEntity.getName());
        recipe.setUsername(recipeEntity.getUsername());
        recipe.setModifiedDate(recipeEntity.getModifiedDate());

        recipe.setInstructions(InstructionMapper.mapToApi(recipeEntity.getApiId(), recipeEntity.getInstructions()));
        recipe.setIngredients(IngredientMapper.mapToApi(recipe.getId(), recipeEntity.getIngredients()));

        return recipe;
    }

    private static RecipeEntity mapToEntity(Recipe recipe) {

        RecipeEntity recipeEntity = new RecipeEntity();
        recipeEntity.setDifficulty(recipe.getDifficulty());
        recipeEntity.setApiId(recipe.getId());
        recipeEntity.setName(recipe.getName());
        recipeEntity.setUsername(recipe.getUsername());
        recipeEntity.setModifiedDate(recipe.getModifiedDate());

        recipeEntity.setInstructions(InstructionMapper.mapToEntity(recipe.getId(), recipe.getInstructions()));
        recipeEntity.setIngredients(IngredientMapper.mapToEntity(recipe.getId(), recipe.getIngredients()));

        return recipeEntity;
    }
}