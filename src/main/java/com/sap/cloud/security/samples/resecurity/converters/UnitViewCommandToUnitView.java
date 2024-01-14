package com.sap.cloud.security.samples.resecurity.converters;

import com.sap.cloud.security.samples.resecurity.commands.UnitViewCommand;
import com.sap.cloud.security.samples.resecurity.model.UnitView;
import io.micrometer.common.lang.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UnitViewCommandToUnitView implements Converter<UnitViewCommand, UnitView> {
    private final UnitCommandToUnit unitConverter;

    public UnitViewCommandToUnitView(UnitCommandToUnit unitConverter) {
        this.unitConverter = unitConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public UnitView convert(UnitViewCommand source) {
        if (source == null) {
            return null;
        }

        final UnitView unitView = new UnitView();
        unitView.setUnitViewCode(source.getUnitViewCode());
        unitView.setViewId(source.getViewId());
        unitView.setViewDescr(source.getViewDescr());
        if (source.getUnitCommands() != null && source.getUnitCommands().size() > 0){
            source.getUnitCommands()
                    .forEach( unitCommand -> unitView.getUnits().add(unitConverter.convert(unitCommand)));
        }
        return unitView;
    }
}