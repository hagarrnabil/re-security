package com.sap.cloud.security.samples.resecurity.commands;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class AreaMasterDetailCommand implements Serializable {
    private Long areaCode;
    private Long projectAreaCode;
    private Long buildingAreaCode;
    private Long unitAreaCode;
    private Long measurementCode;
    private String areaMaster;
    private String description;
    private String projectFlag;
    private String buildingFlag;
    private String unitFlag;
    @JsonIgnore
    private UnitOfMeasurementCommand unitOfMeasurementCommand;
    @JsonIgnore
    private Set<UnitCommand> unitCommands = new HashSet<>();
}
