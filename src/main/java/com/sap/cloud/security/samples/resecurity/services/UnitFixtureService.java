package com.sap.cloud.security.samples.resecurity.services;

import com.sap.cloud.security.samples.resecurity.commands.UnitFixtureCommand;
import com.sap.cloud.security.samples.resecurity.model.UnitFixture;

import java.util.Set;

public interface UnitFixtureService {
    Set<UnitFixtureCommand> getUnitFixtureCommands();

    UnitFixture findById(Long l);

    void deleteById(Long idToDelete);

    UnitFixtureCommand saveUnitFixtureCommand(UnitFixtureCommand command);
    UnitFixture updateUnitFixture(UnitFixtureCommand newUnitFixtureCommand, Long l);

    UnitFixtureCommand findUnitFixtureCommandById(Long l);
}