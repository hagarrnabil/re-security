package com.sap.cloud.security.samples.resecurity.converters;

import com.sap.cloud.security.samples.resecurity.commands.UnitStatusCommand;
import com.sap.cloud.security.samples.resecurity.model.UnitStatus;
import io.micrometer.common.lang.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UnitStatusCommandToUnitStatus implements Converter<UnitStatusCommand, UnitStatus> {
    private final UnitCommandToUnit unitConverter;

    public UnitStatusCommandToUnitStatus(UnitCommandToUnit unitConverter) {
        this.unitConverter = unitConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public UnitStatus convert(UnitStatusCommand source) {
        if (source == null) {
            return null;
        }

        final UnitStatus unitStatus = new UnitStatus();
        unitStatus.setUnitStatusCode(source.getUnitStatusCode());
        unitStatus.setStatusId(source.getStatusId());
        unitStatus.setStatusDescr(source.getStatusDescr());
        if (source.getUnitCommands() != null && source.getUnitCommands().size() > 0){
            source.getUnitCommands()
                    .forEach( unitCommand -> unitStatus.getUnits().add(unitConverter.convert(unitCommand)));
        }
        return unitStatus;
    }

}
