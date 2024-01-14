package com.sap.cloud.security.samples.resecurity.converters;

import com.sap.cloud.security.samples.resecurity.commands.LocationCommand;
import com.sap.cloud.security.samples.resecurity.model.Location;
import io.micrometer.common.lang.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LocationCommandToLocation implements Converter<LocationCommand, Location> {

    @Synchronized
    @Nullable
    @Override
    public Location convert(LocationCommand source) {
        if (source == null) {
            return null;
        }

        final Location location = new Location();
        location.setLocationCode(source.getLocationCode());
        location.setLocationId(source.getLocationId());
        location.setRegionalLocation(source.getRegionalLocation());
        return location;
    }
}
