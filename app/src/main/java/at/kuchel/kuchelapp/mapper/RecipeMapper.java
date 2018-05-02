package at.kuchel.kuchelapp.mapper;

import java.util.ArrayList;
import java.util.List;

import at.kuchel.kuchelapp.api.Image;
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
        recipe.setId(recipeEntity.getId());
        recipe.setName(recipeEntity.getName());
        recipe.setUsername(recipeEntity.getUsername());
        recipe.setModifiedDate(recipeEntity.getModifiedDate());
        recipe.setDuration(recipeEntity.getDuration());

        recipe.setInstructions(InstructionMapper.mapToApi(recipeEntity.getId(), recipeEntity.getInstructions()));
        recipe.setIngredients(IngredientMapper.mapToApi(recipe.getId(), recipeEntity.getIngredients()));

        if (recipeEntity.getImageId() != null) {
            Image image = new Image();
            image.setId(recipeEntity.getImageId());
            recipe.getImages().add(image);
        }

        return recipe;
    }

    private static RecipeEntity mapToEntity(Recipe recipe) {

        RecipeEntity recipeEntity = new RecipeEntity();
        recipeEntity.setDifficulty(recipe.getDifficulty());
        recipeEntity.setId(recipe.getId());
        recipeEntity.setName(recipe.getName());
        recipeEntity.setUsername(recipe.getUsername());
        recipeEntity.setModifiedDate(recipe.getModifiedDate());
        recipeEntity.setDuration(recipe.getDuration());

        recipeEntity.setInstructions(InstructionMapper.mapToEntity(recipe.getId(), recipe.getInstructions()));
        recipeEntity.setIngredients(IngredientMapper.mapToEntity(recipe.getId(), recipe.getIngredients()));
        if (recipe.getImages().size() > 0) {
            recipeEntity.setImageId(recipe.getImages().get(0).getId());
        }
        return recipeEntity;
    }

    public static String getDifficultyAsString(String difficultyAsNumber) {
        if (difficultyAsNumber.equals(Difficulty.VERY_EASY.getValue())) {
            return Difficulty.VERY_EASY.getName();
        } else if (difficultyAsNumber.equals(Difficulty.EASY.getValue())) {
            return Difficulty.EASY.getName();
        } else if (difficultyAsNumber.equals(Difficulty.NORMAL.getValue())) {
            return Difficulty.NORMAL.getName();
        } else if (difficultyAsNumber.equals(Difficulty.HARD.getValue())) {
            return Difficulty.HARD.getName();
        } else {
            return Difficulty.VERY_HARD.getName();
        }
    }

    private enum Difficulty {
        VERY_EASY("1", "sehr leicht"),
        EASY("2", "leicht"),
        NORMAL("3", "mittel"),
        HARD("4", "schwer"),
        VERY_HARD("5", "sehr schwer");


        private final String value;
        private final String identifier;

        private Difficulty(String value, String identifier) {
            this.value = value;
            this.identifier = identifier;
        }

        public String getValue() {
            return value;
        }

        public String getName() {
            return identifier;
        }
    }
}