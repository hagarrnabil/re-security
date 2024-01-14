package com.sap.cloud.security.samples.resecurity.services;

import com.sap.cloud.security.samples.resecurity.commands.UnitOfMeasurementCommand;
import com.sap.cloud.security.samples.resecurity.model.UnitOfMeasurement;

import java.util.Set;

public interface MeasurementService {
    Set<UnitOfMeasurementCommand> getUOMCommands();

    UnitOfMeasurement findById(Long l);

    void deleteById(Long idToDelete);

    UnitOfMeasurementCommand saveUOMCommand(UnitOfMeasurementCommand command);
    UnitOfMeasurement updateUOM(UnitOfMeasurementCommand newMeasurementCommand, Long l);

    UnitOfMeasurementCommand findUOMCommandById(Long l);
}
