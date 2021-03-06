package at.kuchel.kuchelapp.model;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import java.util.List;

/**
 * Created by bernhard on 19.03.2018.
 */

public class RecipeIngredient {

    @Embedded
    public RecipeEntity recipeEntity;
    @Relation(parentColumn = "id", entityColumn = "recipe_id", entity = IngredientEntity.class)
    public List<IngredientEntity> ingredientEntities;
}
