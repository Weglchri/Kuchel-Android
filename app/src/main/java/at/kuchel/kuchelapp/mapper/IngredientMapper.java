package at.kuchel.kuchelapp.mapper;

import java.util.ArrayList;
import java.util.List;

import at.kuchel.kuchelapp.api.IngredientResponse;
import at.kuchel.kuchelapp.api.InstructionResponse;
import at.kuchel.kuchelapp.model.Ingredient;
import at.kuchel.kuchelapp.model.Instruction;

/**
 * Created by bernhard on 20.03.2018.
 */

public class IngredientMapper {
    public static List<Ingredient> map(Long recipeId, List<IngredientResponse> ingredientsResponse) {
        List<Ingredient> ingredients = new ArrayList<>();
        for (IngredientResponse ingredientResponse : ingredientsResponse) {
            ingredients.add(map(recipeId, ingredientResponse));
        }
        return ingredients;
    }

    private static Ingredient map(Long recipeId, IngredientResponse ingredientResponse) {
        Ingredient ingredient = new Ingredient();
        ingredient.setDescription(ingredientResponse.getDescription());
        ingredient.setName(ingredientResponse.getName());
        ingredient.setQualifier(ingredientResponse.getQualifier());
        ingredient.setQuantity(ingredientResponse.getQualifier());
        ingredient.setRecipeId(Integer.parseInt(String.valueOf(recipeId)));
        return ingredient;
    }
}