package com.sap.cloud.security.samples.resecurity.converters;


import com.sap.cloud.security.samples.resecurity.commands.UnitFixtureCommand;
import com.sap.cloud.security.samples.resecurity.model.UnitFixture;
import io.micrometer.common.lang.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UnitFixtureToUnitFixtureCommand implements Converter<UnitFixture, UnitFixtureCommand> {
    private final UnitToUnitCommand unitConverter;

    public UnitFixtureToUnitFixtureCommand(UnitToUnitCommand unitConverter) {
        this.unitConverter = unitConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public UnitFixtureCommand convert(UnitFixture source) {

        if (source == null) {
            return null;
        }

        final UnitFixtureCommand unitFixtureCommand = new UnitFixtureCommand();
        unitFixtureCommand.setUnitFixtureCode(source.getUnitFixtureCode());
        unitFixtureCommand.setFixtureId(source.getFixtureId());
        unitFixtureCommand.setFixtureDescr(source.getFixtureDescr());
        if (source.getUnits() != null && source.getUnits().size() > 0){
            source.getUnits()
                    .forEach(unit -> unitFixtureCommand.getUnitCommands().add(unitConverter.convert(unit)));
        }
        return unitFixtureCommand;
    }
}
