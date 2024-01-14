package com.sap.cloud.security.samples.resecurity.services;

import com.sap.cloud.security.samples.resecurity.commands.LocationCommand;
import com.sap.cloud.security.samples.resecurity.converters.LocationCommandToLocation;
import com.sap.cloud.security.samples.resecurity.converters.LocationToLocationCommand;
import com.sap.cloud.security.samples.resecurity.model.Location;
import com.sap.cloud.security.samples.resecurity.repositories.LocationRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class LocationServiceImpl implements LocationService{
    private final LocationToLocationCommand locationToLocationCommand;
    private final LocationCommandToLocation locationCommandToLocation;
    private final LocationRepository locationRepository;

    public LocationServiceImpl(LocationToLocationCommand locationToLocationCommand, LocationCommandToLocation locationCommandToLocation, LocationRepository locationRepository) {
        this.locationToLocationCommand = locationToLocationCommand;
        this.locationCommandToLocation = locationCommandToLocation;
        this.locationRepository = locationRepository;
    }

    @Override
    @Transactional
    public Set<LocationCommand> getLocationCommands() {
        return StreamSupport.stream(locationRepository.findAll()
                        .spliterator(), false)
                .map(locationToLocationCommand::convert)
                .collect(Collectors.toSet());
    }

    @Override
    public Location findById(Long l) {
        Optional<Location> locationOptional = locationRepository.findById(l);

        if (!locationOptional.isPresent()) {
            throw new RuntimeException("Location Not Found!");
        }

        return locationOptional.get();
    }

    @Override
    public void deleteById(Long idToDelete) {
        locationRepository.deleteById(idToDelete);
    }

    @Override
    @Transactional
    public LocationCommand saveLocationCommand(LocationCommand command) {

        Location detachedLocation = locationCommandToLocation.convert(command);
        Location savedLocation = locationRepository.save(detachedLocation);
        log.debug("Saved Location Id:" + savedLocation.getLocationCode());
        return locationToLocationCommand.convert(savedLocation);
    }

    @Override
    public Location updateLocation(LocationCommand newLocationCommand, Long l) {
        return locationRepository.findById(l).map(oldLocation -> {
            if (newLocationCommand.getLocationId() != oldLocation.getLocationId()) oldLocation.setLocationId(newLocationCommand.getLocationId());
            if (newLocationCommand.getRegionalLocation() != oldLocation.getRegionalLocation()) oldLocation.setRegionalLocation(newLocationCommand.getRegionalLocation());
            return locationRepository.save(oldLocation);
        }).orElseThrow(() -> new RuntimeException("Location not found"));
    }


    @Override
    @Transactional
    public LocationCommand findLocationCommandById(Long l) {
        return locationToLocationCommand.convert(findById(l));
    }
}
