package com.sap.cloud.security.samples.resecurity.services;

import com.sap.cloud.security.samples.resecurity.commands.UnitAreaCommand;
import com.sap.cloud.security.samples.resecurity.model.UnitArea;

import java.util.Set;

public interface UnitAreaService {
    Set<UnitAreaCommand> getUnitAreaCommands();

    UnitArea findById(Long l);

    void deleteById(Long idToDelete);

    UnitAreaCommand saveUnitAreaCommand(UnitAreaCommand command);
    UnitArea updateUnitArea(UnitAreaCommand newUnitAreaCommand, Long l);

    UnitAreaCommand findUnitAreaCommandById(Long l);
}
