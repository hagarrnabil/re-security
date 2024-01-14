package com.sap.cloud.security.samples.resecurity.commands;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
public class BuildingCommand implements Serializable {
    private Long buildingCode;
    private Long profitCode;
    private Long projectCode;
    private Long buildingTypeCode;
    private String buildingId;
    private String buildingDescription;
    private String oldNumber;
    private LocalDate validFrom;
    private Integer numberOfFloors;
    private String profit;
//    private ProjectCommand projectCommand;
    @JsonIgnore
    private Set<UnitCommand> unitCommands = new HashSet<>();
//    private BuildingTypeCommand buildingTypeCommand;
//    private ProfitCenterCommand profitCenterCommand;
}
