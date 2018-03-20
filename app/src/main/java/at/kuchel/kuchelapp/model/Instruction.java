package at.kuchel.kuchelapp.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Relation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bernhard on 12.03.2018.
 */

@Entity
public class Instruction {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "recipe_id")
    private int recipeId;

    @ColumnInfo(name = "step")
    private String step;

    @ColumnInfo(name = "description")
    private String description;

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }
}
