package at.kuchel.kuchelapp.api;

import java.util.ArrayList;
import java.util.List;

public class RecipeResponse {

    private Long id;

    private String username;

    private String name;

    private String duration;

    private String difficulty;

    private List<InstructionResponse> instructions = new ArrayList<>();

    private List<IngredientResponse> ingredients = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public List<InstructionResponse> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<InstructionResponse> instructions) {
        this.instructions = instructions;
    }

    public List<IngredientResponse> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientResponse> ingredients) {
        this.ingredients = ingredients;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
