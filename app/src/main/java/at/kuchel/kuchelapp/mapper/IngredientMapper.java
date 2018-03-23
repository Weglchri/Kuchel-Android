package at.kuchel.kuchelapp.mapper;

import java.util.ArrayList;
import java.util.List;

import at.kuchel.kuchelapp.api.Ingredient;
import at.kuchel.kuchelapp.model.IngredientEntity;

/**
 * Created by bernhard on 20.03.2018.
 */

public class IngredientMapper {

    public static List<IngredientEntity> mapToEntity(Long recipeId, List<Ingredient> ingredientsResponse) {
        List<IngredientEntity> ingredientEntities = new ArrayList<>();
        for (Ingredient ingredient : ingredientsResponse) {
            ingredientEntities.add(mapToEntity(recipeId, ingredient));
        }
        return ingredientEntities;
    }

    private static IngredientEntity mapToEntity(Long recipeId, Ingredient ingredient) {
        IngredientEntity ingredientEntity = new IngredientEntity();
        ingredientEntity.setDescription(ingredient.getDescription());
        ingredientEntity.setName(ingredient.getName());
        ingredientEntity.setQualifier(ingredient.getQualifier());
        ingredientEntity.setQuantity(ingredient.getQuantity());
        ingredientEntity.setRecipeId(Integer.parseInt(String.valueOf(recipeId)));
        return ingredientEntity;
    }

    public static List<Ingredient> mapToApi(Long id, List<IngredientEntity> ingredientEntities) {
        List<Ingredient> ingredients = new ArrayList<>();
        for (IngredientEntity ingredientEntity : ingredientEntities) {
            ingredients.add(mapToApi(id, ingredientEntity));
        }
        return ingredients;
    }

    private static Ingredient mapToApi(Long recipeId, IngredientEntity ingredientEntity) {
        Ingredient ingredient = new Ingredient();
        ingredient.setDescription(ingredientEntity.getDescription());
        ingredient.setName(ingredientEntity.getName());
        ingredient.setQualifier(ingredientEntity.getQualifier());
        ingredient.setQuantity(ingredientEntity.getQuantity());
        return ingredient;
    }
}