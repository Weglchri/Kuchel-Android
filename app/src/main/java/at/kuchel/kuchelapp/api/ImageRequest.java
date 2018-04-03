package at.kuchel.kuchelapp.api;

import java.util.Date;

import lombok.Data;

@Data
public class ImageRequest {

    private Long recipeId;
    private Date creationDate;
    private byte[] data;
}
