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
public class ProfitCenterCommand implements Serializable {
    private Long profitCode;
    private String profitId;
    private String profitDescr;
    @JsonIgnore
    private Set<ProjectCommand> projectCommands = new HashSet<>();
    @JsonIgnore
    private Set<BuildingCommand> buildingCommands = new HashSet<>();
}
