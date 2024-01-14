package com.sap.cloud.security.samples.resecurity.converters;

import com.sap.cloud.security.samples.resecurity.commands.UnitFixtureCommand;
import com.sap.cloud.security.samples.resecurity.model.UnitFixture;
import io.micrometer.common.lang.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class UnitFixtureCommandToUnitFixture implements Converter<UnitFixtureCommand, UnitFixture> {
    private final UnitCommandToUnit unitConverter;

    public UnitFixtureCommandToUnitFixture(UnitCommandToUnit unitConverter) {
        this.unitConverter = unitConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public UnitFixture convert(UnitFixtureCommand source) {
        if (source == null) {
            return null;
        }

        final UnitFixture unitFixture = new UnitFixture();
        unitFixture.setUnitFixtureCode(source.getUnitFixtureCode());
        unitFixture.setFixtureId(source.getFixtureId());
        unitFixture.setFixtureDescr(source.getFixtureDescr());
        if (source.getUnitCommands() != null && source.getUnitCommands().size() > 0){
            source.getUnitCommands()
                    .forEach( unitCommand -> unitFixture.getUnits().add(unitConverter.convert(unitCommand)));
        }
        return unitFixture;
    }
}
