package com.sap.cloud.security.samples.resecurity.converters;

import com.sap.cloud.security.samples.resecurity.commands.UnitFloorCommand;
import com.sap.cloud.security.samples.resecurity.model.UnitFloor;
import io.micrometer.common.lang.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UnitFloorToUnitFloorCommand implements Converter<UnitFloor, UnitFloorCommand> {
    private final UnitToUnitCommand unitConverter;

    public UnitFloorToUnitFloorCommand(UnitToUnitCommand unitConverter) {
        this.unitConverter = unitConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public UnitFloorCommand convert(UnitFloor source) {

        if (source == null) {
            return null;
        }

        final UnitFloorCommand unitFloorCommand = new UnitFloorCommand();
        unitFloorCommand.setUnitFloorCode(source.getUnitFloorCode());
        unitFloorCommand.setFloorId(source.getFloorId());
        unitFloorCommand.setFloorDescr(source.getFloorDescr());
        if (source.getUnits() != null && source.getUnits().size() > 0){
            source.getUnits()
                    .forEach(unit -> unitFloorCommand.getUnitCommands().add(unitConverter.convert(unit)));
        }
        return unitFloorCommand;
    }
}
