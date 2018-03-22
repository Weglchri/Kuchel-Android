package at.kuchel.kuchelapp.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Relation;

import java.util.ArrayList;
import java.util.List;

@Entity
public class RecipeEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "api_id")
    private Long apiId;

    @ColumnInfo(name = "username")
    private String username;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "duration")
    private String duration;

    @ColumnInfo(name = "difficulty")
    private String difficulty;

    @Ignore
    private List<InstructionEntity> instructions;

    @Ignore
    private List<IngredientEntity> ingredients;

    public Long getApiId() {
        return apiId;
    }

    public void setApiId(Long apiId) {
        this.apiId = apiId;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<InstructionEntity> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<InstructionEntity> instructions) {
        this.instructions = instructions;
    }

    public List<IngredientEntity> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientEntity> ingredients) {
        this.ingredients = ingredients;
    }
}
