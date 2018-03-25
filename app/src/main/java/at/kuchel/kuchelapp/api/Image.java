package at.kuchel.kuchelapp.api;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Date;

import lombok.Data;

/**
 * Created by bernhard on 24.03.2018.
 */
@Data
public class Image {

    private String id;
    private Date modifiedDate;
    private byte[] data;
}
