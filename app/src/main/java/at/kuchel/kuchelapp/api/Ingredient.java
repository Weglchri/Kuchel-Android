package at.kuchel.kuchelapp.api;

import lombok.Data;

/**
 * Created by bernhard on 12.03.2018.
 */

@Data
public class Ingredient {

    private String id;
    private String description;
    private String name;
    private String quantity;
    private String qualifier;

}
