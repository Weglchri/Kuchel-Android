package at.kuchel.kuchelapp.mapper;

import java.util.Date;

import at.kuchel.kuchelapp.api.LastSyncRequest;
import at.kuchel.kuchelapp.model.GlobalParamEntity;

/**
 * Created by bernhard on 31.03.2018.
 */

public class LastSyncMapper {

    public static LastSyncRequest map(GlobalParamEntity globalParamEntity) {
        LastSyncRequest lastSyncRequest = new LastSyncRequest();
        lastSyncRequest.setLastSyncDate(new Date(Long.parseLong(globalParamEntity.getValue())));
        return lastSyncRequest;
    }
}
