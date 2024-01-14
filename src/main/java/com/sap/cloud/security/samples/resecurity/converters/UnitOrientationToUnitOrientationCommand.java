package com.sap.cloud.security.samples.resecurity.converters;

import com.sap.cloud.security.samples.resecurity.commands.UnitOrientationCommand;
import com.sap.cloud.security.samples.resecurity.model.UnitOrientation;
import io.micrometer.common.lang.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UnitOrientationToUnitOrientationCommand implements Converter<UnitOrientation, UnitOrientationCommand> {
    private final UnitToUnitCommand unitConverter;

    public UnitOrientationToUnitOrientationCommand(UnitToUnitCommand unitConverter) {
        this.unitConverter = unitConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public UnitOrientationCommand convert(UnitOrientation source) {

        if (source == null) {
            return null;
        }

        final UnitOrientationCommand unitOrientationCommand = new UnitOrientationCommand();
        unitOrientationCommand.setUnitOrientationCode(source.getUnitOrientationCode());
        unitOrientationCommand.setOrientationId(source.getOrientationId());
        unitOrientationCommand.setOrientationDescr(source.getOrientationDescr());
        if (source.getUnits() != null && source.getUnits().size() > 0){
            source.getUnits()
                    .forEach(unit -> unitOrientationCommand.getUnitCommands().add(unitConverter.convert(unit)));
        }
        return unitOrientationCommand;
    }

}
