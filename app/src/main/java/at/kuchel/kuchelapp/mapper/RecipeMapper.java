package at.kuchel.kuchelapp.mapper;

import java.util.ArrayList;
import java.util.List;

import at.kuchel.kuchelapp.api.Recipe;
import at.kuchel.kuchelapp.model.RecipeEntity;

/**
 * Created by bernhard on 19.03.2018.
 */

public class RecipeMapper {

    public static List<RecipeEntity> map(List<Recipe> recipes) {
        List<RecipeEntity> recipesDb = new ArrayList<>();
        for (Recipe recipe : recipes) {
            recipesDb.add(map(recipe));
        }
        return recipesDb;
    }

    public static RecipeEntity map(Recipe recipe) {

        RecipeEntity recipeEntityDb = new RecipeEntity();
        recipeEntityDb.setDifficulty(recipe.getDifficulty());
        recipeEntityDb.setApiId(recipe.getId());
        recipeEntityDb.setName(recipe.getName());
        recipeEntityDb.setUsername(recipe.getUsername());

        return recipeEntityDb;
    }
}
