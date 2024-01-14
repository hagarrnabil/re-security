package com.sap.cloud.security.samples.resecurity.controllers;

import com.sap.cloud.security.samples.resecurity.commands.LocationCommand;
import com.sap.cloud.security.samples.resecurity.converters.LocationToLocationCommand;
import com.sap.cloud.security.samples.resecurity.repositories.LocationRepository;
import com.sap.cloud.security.samples.resecurity.services.LocationService;
import jakarta.validation.constraints.NotNull;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;


@RestController
public class LocationController {
    private final LocationRepository locationRepository;
    private final LocationService locationService;
    private final LocationToLocationCommand locationToLocationCommand;

    public LocationController(LocationRepository locationRepository, LocationService locationService,
                              LocationToLocationCommand locationToLocationCommand) {
        this.locationRepository = locationRepository;
        this.locationService = locationService;
        this.locationToLocationCommand = locationToLocationCommand;
    }

    @GetMapping("/locations")
    Set<LocationCommand> all() {
        return locationService.getLocationCommands();
    }

    @GetMapping("/locations/{locationCode}")
    public Optional<LocationCommand> findByIds(@PathVariable @NotNull Long locationCode) {

        return Optional.ofNullable(locationService.findLocationCommandById(locationCode));
    }

    @PostMapping("/locations")
    LocationCommand newLocationCommand(@RequestBody LocationCommand newLocationCommand) {

        LocationCommand savedCommand = locationService.saveLocationCommand(newLocationCommand);
        return savedCommand;

    }

    @DeleteMapping("/locations/{locationCode}")
    void deleteLocationCommand(@PathVariable Long locationCode) {
        locationService.deleteById(locationCode);
    }

    @PutMapping
    @RequestMapping("/locations/{locationCode}")
    @Transactional
    LocationCommand updateLocationCommand(@RequestBody LocationCommand newLocationCommand, @PathVariable Long locationCode) {

        LocationCommand command = locationToLocationCommand.convert(locationService.updateLocation(newLocationCommand, locationCode));
        return command;
    }
}