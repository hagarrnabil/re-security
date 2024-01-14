package com.sap.cloud.security.samples.resecurity.converters;

import com.sap.cloud.security.samples.resecurity.commands.ProjectAreaCommand;
import com.sap.cloud.security.samples.resecurity.model.ProjectArea;
import io.micrometer.common.lang.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProjectAreaCommandToProjectArea implements Converter<ProjectAreaCommand, ProjectArea> {
    private final AreaMasterDetailCommandToAreaMasterDetail areaConverter;

    public ProjectAreaCommandToProjectArea(AreaMasterDetailCommandToAreaMasterDetail areaConverter) {
        this.areaConverter = areaConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public ProjectArea convert(ProjectAreaCommand source) {
        if (source == null) {
            return null;
        }

        final ProjectArea projectArea = new ProjectArea();
        projectArea.setProjectAreaCode(source.getProjectAreaCode());
        projectArea.setProjectArea(source.getProjectArea());
        projectArea.setDescription(source.getDescription());
        if (source.getAreaMasterDetailCommands() != null && source.getAreaMasterDetailCommands().size() > 0){
            source.getAreaMasterDetailCommands()
                    .forEach( areaMasterDetailCommand -> projectArea.getAreaMasterDetails().add(areaConverter.convert(areaMasterDetailCommand)));
        }
        return projectArea;
    }
}
