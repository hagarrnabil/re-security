package com.sap.cloud.security.samples.resecurity.services;

import com.sap.cloud.security.samples.resecurity.commands.UnitFloorCommand;
import com.sap.cloud.security.samples.resecurity.model.UnitFloor;

import java.util.Set;

public interface UnitFloorService {
    Set<UnitFloorCommand> getUnitFloorCommands();

    UnitFloor findById(Long l);

    void deleteById(Long idToDelete);

    UnitFloorCommand saveUnitFloorCommand(UnitFloorCommand command);
    UnitFloor updateUnitFloor(UnitFloorCommand newUnitFloorCommand, Long l);

    UnitFloorCommand findUnitFloorCommandById(Long l);
}
