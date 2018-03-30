package at.kuchel.kuchelapp.api;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class Profile {

    private String username;
    private Date birthday;
    private String mailAddress;
    private List<String> roles;
}
