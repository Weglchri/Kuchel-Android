package at.kuchel.kuchelapp.builder;

import android.content.Context;
import android.widget.TextView;
import at.kuchel.kuchelapp.api.Ingredient;
import at.kuchel.kuchelapp.api.Instruction;
import at.kuchel.kuchelapp.api.Recipe;

public class RecipeBuilder {


    private Recipe recipe;
    private StringBuilder instructions = new StringBuilder();
    private StringBuilder ingredients = new StringBuilder();

    public RecipeBuilder(Recipe recipe) {
        this.recipe = recipe;
    }


    public TextView buildIngredients(Context context) {
        for(Ingredient ingredient : recipe.getIngredients()) {
            ingredients.append(ingredient.getQuantity()).append(" ");
            ingredients.append(ingredient.getQualifier()).append(" ");
            ingredients.append(ingredient.getName());
            ingredients.append("\n");
        }

        final TextView rowTextView = new TextView(context);
        rowTextView.setText(ingredients.toString());

        return rowTextView;
    }


    public TextView buildInstructions(Context context) {
        for(Instruction instruction : recipe.getInstructions()) {
            instructions.append(instruction.getStep() + ". Schritt: ");
            instructions.append("   ");
            instructions.append(instruction.getDescription());
            instructions.append("\n\n");
        }
        final TextView rowTextView = new TextView(context);
        rowTextView.setText(instructions.toString());

        return rowTextView;
    }

}
