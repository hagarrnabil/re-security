package com.sap.cloud.security.samples.resecurity.converters;

import com.sap.cloud.security.samples.resecurity.commands.UnitStatusCommand;
import com.sap.cloud.security.samples.resecurity.model.UnitStatus;
import io.micrometer.common.lang.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UnitStatusToUnitStatusCommand implements Converter<UnitStatus, UnitStatusCommand> {
    private final UnitToUnitCommand unitConverter;

    public UnitStatusToUnitStatusCommand(UnitToUnitCommand unitConverter) {
        this.unitConverter = unitConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public UnitStatusCommand convert(UnitStatus source) {
        if (source == null) {
            return null;
        }

        final UnitStatusCommand unitStatusCommand = new UnitStatusCommand();
        unitStatusCommand.setUnitStatusCode(source.getUnitStatusCode());
        unitStatusCommand.setStatusId(source.getStatusId());
        unitStatusCommand.setStatusDescr(source.getStatusDescr());
        if (source.getUnits() != null && source.getUnits().size() > 0){
            source.getUnits()
                    .forEach(unit -> unitStatusCommand.getUnitCommands().add(unitConverter.convert(unit)));
        }
        return unitStatusCommand;
    }
}
