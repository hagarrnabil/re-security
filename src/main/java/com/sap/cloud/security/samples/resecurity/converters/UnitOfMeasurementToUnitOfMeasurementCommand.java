package com.sap.cloud.security.samples.resecurity.converters;

import com.sap.cloud.security.samples.resecurity.commands.UnitOfMeasurementCommand;
import com.sap.cloud.security.samples.resecurity.model.UnitOfMeasurement;
import io.micrometer.common.lang.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasurementToUnitOfMeasurementCommand implements Converter<UnitOfMeasurement, UnitOfMeasurementCommand> {

    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasurementCommand convert(UnitOfMeasurement source) {
        if (source == null) {
            return null;
        }

        final UnitOfMeasurementCommand measurementCommand = new UnitOfMeasurementCommand();
        measurementCommand.setMeasurementCode(source.getMeasurementCode());
        measurementCommand.setUomID(source.getUomID());
        measurementCommand.setUomDescr(source.getUomDescr());
        return measurementCommand;
    }
}
