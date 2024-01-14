package com.sap.cloud.security.samples.resecurity.converters;

import com.sap.cloud.security.samples.resecurity.commands.UnitFloorCommand;
import com.sap.cloud.security.samples.resecurity.model.UnitFloor;
import io.micrometer.common.lang.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UnitFloorCommandToUnitFloor implements Converter<UnitFloorCommand, UnitFloor> {
    private final UnitCommandToUnit unitConverter;

    public UnitFloorCommandToUnitFloor(UnitCommandToUnit unitConverter) {
        this.unitConverter = unitConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public UnitFloor convert(UnitFloorCommand source) {
        if (source == null) {
            return null;
        }

        final UnitFloor unitFloor = new UnitFloor();
        unitFloor.setUnitFloorCode(source.getUnitFloorCode());
        unitFloor.setFloorId(source.getFloorId());
        unitFloor.setFloorDescr(source.getFloorDescr());
        if (source.getUnitCommands() != null && source.getUnitCommands().size() > 0){
            source.getUnitCommands()
                    .forEach( unitCommand -> unitFloor.getUnits().add(unitConverter.convert(unitCommand)));
        }
        return unitFloor;
    }
}
