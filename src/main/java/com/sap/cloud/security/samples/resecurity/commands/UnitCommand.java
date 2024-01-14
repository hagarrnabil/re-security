package com.sap.cloud.security.samples.resecurity.commands;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
public class UnitCommand implements Serializable {
    private Long unitCode;
    private Long buildingCode;
    private Long areaCode;
    private Long unitFixtureCode;
    private Long unitFloorCode;
    private Long unitOrientationCode;
    private Long unitStatusCode;
    private Long unitSubtypeCode;
    private Long unitViewCode;
    private Long usageTypeCode;
    private String unitKey;
    private String oldNumber;
    private String description;
    private LocalDate blockingDate;
    private String blockingReason;
    private String salesPhase;
    private LocalDate constructionDate;
    private LocalDate unitDeliveryDate;
    private String area;
    private Integer areaValue;
    private Integer noOfRooms;
    private Integer price;
    private LocalDate validFrom;
    private Integer fromFloor;
    private Integer toFloor;
//    private BuildingCommand buildingCommand;
//    private UsageTypeCommand usageTypeCommand;
//    private UnitOrientationCommand unitOrientationCommand;
//    private UnitFixtureCommand unitFixtureCommand;
//    private UnitStatusCommand unitStatusCommand;
//    private UnitViewCommand unitViewCommand;
//    private UnitSubtypeCommand unitSubtypeCommand;
//    private UnitFloorCommand unitFloorCommand;
//    private AreaMasterDetailCommand areaMasterDetailCommand;
}
