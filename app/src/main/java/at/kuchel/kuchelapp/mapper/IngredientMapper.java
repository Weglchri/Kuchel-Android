package at.kuchel.kuchelapp.mapper;

import java.util.ArrayList;
import java.util.List;

import at.kuchel.kuchelapp.api.Ingredient;
import at.kuchel.kuchelapp.model.IngredientEntity;

/**
 * Created by bernhard on 20.03.2018.
 */

public class IngredientMapper {
    public static List<IngredientEntity> map(Long recipeId, List<Ingredient> ingredientsResponse) {
        List<IngredientEntity> ingredientEntities = new ArrayList<>();
        for (Ingredient ingredient : ingredientsResponse) {
            ingredientEntities.add(map(recipeId, ingredient));
        }
        return ingredientEntities;
    }

    private static IngredientEntity map(Long recipeId, Ingredient ingredient) {
        IngredientEntity ingredientEntity = new IngredientEntity();
        ingredientEntity.setDescription(ingredient.getDescription());
        ingredientEntity.setName(ingredient.getName());
        ingredientEntity.setQualifier(ingredient.getQualifier());
        ingredientEntity.setQuantity(ingredient.getQuantity());
        ingredientEntity.setRecipeId(Integer.parseInt(String.valueOf(recipeId)));
        return ingredientEntity;
    }
}