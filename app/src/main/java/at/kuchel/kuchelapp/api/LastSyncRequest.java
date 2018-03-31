package at.kuchel.kuchelapp.api;

import java.util.Date;

import lombok.Data;

@Data
public class LastSyncRequest {

    private Date lastSyncDate;
}