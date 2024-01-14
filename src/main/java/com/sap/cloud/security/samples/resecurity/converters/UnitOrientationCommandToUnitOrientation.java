package com.sap.cloud.security.samples.resecurity.converters;

import com.sap.cloud.security.samples.resecurity.commands.UnitOrientationCommand;
import com.sap.cloud.security.samples.resecurity.model.UnitOrientation;
import io.micrometer.common.lang.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UnitOrientationCommandToUnitOrientation implements Converter<UnitOrientationCommand, UnitOrientation> {
    private final UnitCommandToUnit unitConverter;

    public UnitOrientationCommandToUnitOrientation(UnitCommandToUnit unitConverter) {
        this.unitConverter = unitConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public UnitOrientation convert(UnitOrientationCommand source) {
        if (source == null) {
            return null;
        }

        final UnitOrientation unitOrientation = new UnitOrientation();
        unitOrientation.setUnitOrientationCode(source.getUnitOrientationCode());
        unitOrientation.setOrientationId(source.getOrientationId());
        unitOrientation.setOrientationDescr(source.getOrientationDescr());
        if (source.getUnitCommands() != null && source.getUnitCommands().size() > 0){
            source.getUnitCommands()
                    .forEach( unitCommand -> unitOrientation.getUnits().add(unitConverter.convert(unitCommand)));
        }
        return unitOrientation;
    }

}
