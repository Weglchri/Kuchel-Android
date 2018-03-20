package at.kuchel.kuchelapp.mapper;

import java.util.ArrayList;
import java.util.List;

import at.kuchel.kuchelapp.api.RecipeResponse;
import at.kuchel.kuchelapp.model.Recipe;

/**
 * Created by bernhard on 19.03.2018.
 */

public class RecipeMapper {

    public static List<Recipe> map(List<RecipeResponse> recipes) {
        List<Recipe> recipesDb = new ArrayList<>();
        for (RecipeResponse recipe : recipes) {
            recipesDb.add(map(recipe));
        }
        return recipesDb;
    }

    public static Recipe map(RecipeResponse recipe) {

        Recipe recipeDb = new Recipe();
        recipeDb.setDifficulty(recipe.getDifficulty());
        recipeDb.setApiId(recipe.getId());
        recipeDb.setName(recipe.getName());
        recipeDb.setUsername(recipe.getUsername());

        return recipeDb;
    }
}
