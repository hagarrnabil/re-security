package com.sap.cloud.security.samples.resecurity.services;

import com.sap.cloud.security.samples.resecurity.commands.UnitOrientationCommand;
import com.sap.cloud.security.samples.resecurity.model.UnitOrientation;

import java.util.Set;

public interface UnitOrientationService {
    Set<UnitOrientationCommand> getUnitOrientationCommands();

    UnitOrientation findById(Long l);

    void deleteById(Long idToDelete);

    UnitOrientationCommand saveUnitOrientationCommand(UnitOrientationCommand command);
    UnitOrientation updateUnitOrientation(UnitOrientationCommand newUnitOrientationCommand, Long l);

    UnitOrientationCommand findUnitOrientationCommandById(Long l);
}
