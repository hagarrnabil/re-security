package com.sap.cloud.security.samples.resecurity.services;

import com.sap.cloud.security.samples.resecurity.commands.LocationCommand;
import com.sap.cloud.security.samples.resecurity.model.Location;

import java.util.Set;

public interface LocationService {
    Set<LocationCommand> getLocationCommands();

    Location findById(Long l);

    void deleteById(Long idToDelete);

    LocationCommand saveLocationCommand(LocationCommand command);
    Location updateLocation(LocationCommand newLocationCommand, Long l);

    LocationCommand findLocationCommandById(Long l);
}
