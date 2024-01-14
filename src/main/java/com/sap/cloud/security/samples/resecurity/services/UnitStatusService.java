package com.sap.cloud.security.samples.resecurity.services;

import com.sap.cloud.security.samples.resecurity.commands.UnitStatusCommand;
import com.sap.cloud.security.samples.resecurity.model.UnitStatus;

import java.util.Set;

public interface UnitStatusService {
    Set<UnitStatusCommand> getUnitStatusCommands();

    UnitStatus findById(Long l);

    void deleteById(Long idToDelete);

    UnitStatusCommand saveUnitStatusCommand(UnitStatusCommand command);
    UnitStatus updateUnitStatus(UnitStatusCommand newUnitStatusCommand, Long l);

    UnitStatusCommand findUnitStatusCommandById(Long l);
}
