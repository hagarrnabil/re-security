package com.sap.cloud.security.samples.resecurity.converters;

import com.sap.cloud.security.samples.resecurity.commands.LocationCommand;
import com.sap.cloud.security.samples.resecurity.model.Location;
import io.micrometer.common.lang.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LocationToLocationCommand implements Converter<Location, LocationCommand> {

    @Synchronized
    @Nullable
    @Override
    public LocationCommand convert(Location source) {
        if (source == null) {
            return null;
        }

        final LocationCommand locationCommand = new LocationCommand();
        locationCommand.setLocationCode(source.getLocationCode());
        locationCommand.setLocationId(source.getLocationId());
        locationCommand.setRegionalLocation(source.getRegionalLocation());
        return locationCommand;

    }
}
