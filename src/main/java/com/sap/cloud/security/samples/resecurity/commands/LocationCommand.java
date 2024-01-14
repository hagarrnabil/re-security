package com.sap.cloud.security.samples.resecurity.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LocationCommand {
    private Long locationCode;
    private String locationId;
    private String regionalLocation;
}
