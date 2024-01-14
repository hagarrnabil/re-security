package com.sap.cloud.security.samples.resecurity.converters;

import com.sap.cloud.security.samples.resecurity.commands.UnitOfMeasurementCommand;
import com.sap.cloud.security.samples.resecurity.model.UnitOfMeasurement;
import io.micrometer.common.lang.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasurementCommandToUnitOfMeasurement implements Converter<UnitOfMeasurementCommand, UnitOfMeasurement> {

    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasurement convert(UnitOfMeasurementCommand source) {

        if (source == null) {
            return null;
        }

        final UnitOfMeasurement measurement = new UnitOfMeasurement();
        measurement.setMeasurementCode(source.getMeasurementCode());
        measurement.setUomID(source.getUomID());
        measurement.setUomDescr(source.getUomDescr());
        return measurement;

    }
}
