package at.kuchel.kuchelapp.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class Recipe {

    private Long id;

    private String username;

    private String name;

    private String duration;

    private String difficulty;

    private Date modifiedDate;

    private List<Instruction> instructions = new ArrayList<>();

    private List<Ingredient> ingredients = new ArrayList<>();

    private List<Image> images = new ArrayList<>();
}
