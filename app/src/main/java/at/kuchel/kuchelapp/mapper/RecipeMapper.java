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

    public static RecipeEntity mapToEntity(Recipe recipe) {

        RecipeEntity recipeEntity = new RecipeEntity();
        recipeEntity.setDifficulty(recipe.getDifficulty());
        recipeEntity.setApiId(recipe.getId());
        recipeEntity.setName(recipe.getName());
        recipeEntity.setUsername(recipe.getUsername());

        recipeEntity.setInstructions(InstructionMapper.map(recipe.getId(), recipe.getInstructions()));
        recipeEntity.setIngredients(IngredientMapper.map(recipe.getId(), recipe.getIngredients()));

        return recipeEntity;
    }
}
