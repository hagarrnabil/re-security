package com.sap.cloud.security.samples.resecurity.converters;

import com.sap.cloud.security.samples.resecurity.commands.UnitSubtypeCommand;
import com.sap.cloud.security.samples.resecurity.model.UnitSubtype;
import io.micrometer.common.lang.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UnitSubtypeCommandToUnitSubtype implements Converter<UnitSubtypeCommand, UnitSubtype> {
    private final UnitCommandToUnit unitConverter;

    public UnitSubtypeCommandToUnitSubtype(UnitCommandToUnit unitConverter) {
        this.unitConverter = unitConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public UnitSubtype convert(UnitSubtypeCommand source) {
        if (source == null) {
            return null;
        }

        final UnitSubtype unitSubtype = new UnitSubtype();
        unitSubtype.setUnitSubtypeCode(source.getUnitSubtypeCode());
        unitSubtype.setSubtypeId(source.getSubtypeId());
        unitSubtype.setSubtypeDescr(source.getSubtypeDescr());
        if (source.getUnitCommands() != null && source.getUnitCommands().size() > 0){
            source.getUnitCommands()
                    .forEach( unitCommand -> unitSubtype.getUnits().add(unitConverter.convert(unitCommand)));
        }
        return unitSubtype;
    }
}
