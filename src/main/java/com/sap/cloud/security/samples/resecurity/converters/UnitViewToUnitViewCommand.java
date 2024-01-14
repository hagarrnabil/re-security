package com.sap.cloud.security.samples.resecurity.converters;

import com.sap.cloud.security.samples.resecurity.commands.UnitViewCommand;
import com.sap.cloud.security.samples.resecurity.model.UnitView;
import io.micrometer.common.lang.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UnitViewToUnitViewCommand implements Converter<UnitView, UnitViewCommand> {
    private final UnitToUnitCommand unitConverter;

    public UnitViewToUnitViewCommand(UnitToUnitCommand unitConverter) {
        this.unitConverter = unitConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public UnitViewCommand convert(UnitView source) {

        if (source == null) {
            return null;
        }

        final UnitViewCommand unitViewCommand = new UnitViewCommand();
        unitViewCommand.setUnitViewCode(source.getUnitViewCode());
        unitViewCommand.setViewId(source.getViewId());
        unitViewCommand.setViewDescr(source.getViewDescr());
        if (source.getUnits() != null && source.getUnits().size() > 0){
            source.getUnits()
                    .forEach(unit -> unitViewCommand.getUnitCommands().add(unitConverter.convert(unit)));
        }
        return unitViewCommand;
    }
}
