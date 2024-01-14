package com.sap.cloud.security.samples.resecurity.services;

import com.sap.cloud.security.samples.resecurity.commands.UnitCommand;
import com.sap.cloud.security.samples.resecurity.model.Unit;

import java.util.Set;

public interface UnitService {
    Set<UnitCommand> getUnitCommands();

    Unit findById(Long l);

    void deleteById(Long idToDelete);

    UnitCommand saveUnitCommand(UnitCommand command);
    Unit updateUnit(Unit newUnit, Long l);

    UnitCommand findUnitCommandById(Long l);
}
