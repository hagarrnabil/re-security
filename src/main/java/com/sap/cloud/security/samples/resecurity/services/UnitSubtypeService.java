package com.sap.cloud.security.samples.resecurity.services;

import com.sap.cloud.security.samples.resecurity.commands.UnitSubtypeCommand;
import com.sap.cloud.security.samples.resecurity.model.UnitSubtype;

import java.util.Set;

public interface UnitSubtypeService {
    Set<UnitSubtypeCommand> getUnitSubtypeCommands();

    UnitSubtype findById(Long l);

    void deleteById(Long idToDelete);

    UnitSubtypeCommand saveUnitSubtypeCommand(UnitSubtypeCommand command);
    UnitSubtype updateUnitSubtype(UnitSubtypeCommand newUnitSubtypeCommand, Long l);

    UnitSubtypeCommand findUnitSubtypeCommandById(Long l);
}
