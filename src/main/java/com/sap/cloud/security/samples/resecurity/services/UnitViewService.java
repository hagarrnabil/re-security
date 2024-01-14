package com.sap.cloud.security.samples.resecurity.services;

import com.sap.cloud.security.samples.resecurity.commands.UnitViewCommand;
import com.sap.cloud.security.samples.resecurity.model.UnitView;

import java.util.Set;

public interface UnitViewService {
    Set<UnitViewCommand> getUnitViewCommands();

    UnitView findById(Long l);

    void deleteById(Long idToDelete);

    UnitViewCommand saveUnitViewCommand(UnitViewCommand command);

    UnitView updateUnitView(UnitViewCommand newUnitViewCommand, Long l);

    UnitViewCommand findUnitViewCommandById(Long l);
}
