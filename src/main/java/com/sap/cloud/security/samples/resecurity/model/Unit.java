package com.sap.cloud.security.samples.resecurity.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Data
@EqualsAndHashCode(exclude = {"building","usageType","unitOrientation","unitFixture","unitStatus",
        "unitView","unitSubtype","unitFloor","areaMasterDetail"})
@Table(name = "unit")
public class Unit implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long unitCode;


    @Column(unique = true, length = 8, columnDefinition = "char(8)")
    @Length(max = 8)
    private String unitKey;
    @Column(length = 8, columnDefinition = "char(8)")
    @Length(max = 8)
    private String oldNumber;
//    @NotNull
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
    private Long buildingCode;
    private Long unitOrientationCode;
    private Long unitFixtureCode;
    private Long unitStatusCode;
    private Long usageTypeCode;
    private Long unitViewCode;
    private Long unitSubtypeCode;
    private Long unitFloorCode;
    private Long areaCode;

    @ManyToOne
    private Building building;
    @ManyToOne
    private UsageType usageType;
    @ManyToOne
    private UnitOrientation unitOrientation;
    @ManyToOne
    private UnitFixture unitFixture;
    @ManyToOne
    private UnitStatus unitStatus;
    @ManyToOne
    private UnitView unitView;
    @ManyToOne
    private UnitSubtype unitSubtype;
    @ManyToOne
    private UnitFloor unitFloor;
    @ManyToOne
    private AreaMasterDetail areaMasterDetail;
    @ManyToOne
    private UnitPaymentDetails unitPaymentDetails;

    public Unit() {
    }

    public Unit(String unitKey, String oldNumber, String description, LocalDate blockingDate, String blockingReason, String salesPhase, LocalDate constructionDate, LocalDate unitDeliveryDate, String area, Integer areaValue,
                Integer noOfRooms, Integer price, LocalDate validFrom, Integer fromFloor, Integer toFloor, Building building) {
        this.unitKey = unitKey;
        this.oldNumber = oldNumber;
        this.description = description;
        this.blockingDate = blockingDate;
        this.blockingReason = blockingReason;
        this.salesPhase = salesPhase;
        this.constructionDate = constructionDate;
        this.unitDeliveryDate = unitDeliveryDate;
        this.area = area;
        this.areaValue = areaValue;
        this.noOfRooms = noOfRooms;
        this.price = price;
        this.validFrom = validFrom;
        this.fromFloor = fromFloor;
        this.toFloor = toFloor;
        this.building = building;
    }

    public Unit(String unitKey, String oldNumber, String description, LocalDate blockingDate, String blockingReason, String salesPhase, LocalDate constructionDate, LocalDate unitDeliveryDate, String area, Integer areaValue, Integer noOfRooms,
                Integer price, LocalDate validFrom, Integer fromFloor, Integer toFloor, Building building, UsageType usageType, UnitOrientation unitOrientation, UnitFixture unitFixture, UnitStatus unitStatus, UnitView unitView, UnitSubtype unitSubtype,
                UnitFloor unitFloor, AreaMasterDetail areaMasterDetail, UnitPaymentDetails unitPaymentDetails) {
        this.unitKey = unitKey;
        this.oldNumber = oldNumber;
        this.description = description;
        this.blockingDate = blockingDate;
        this.blockingReason = blockingReason;
        this.salesPhase = salesPhase;
        this.constructionDate = constructionDate;
        this.unitDeliveryDate = unitDeliveryDate;
        this.area = area;
        this.areaValue = areaValue;
        this.noOfRooms = noOfRooms;
        this.price = price;
        this.validFrom = validFrom;
        this.fromFloor = fromFloor;
        this.toFloor = toFloor;
        this.building = building;
        this.usageType = usageType;
        this.unitOrientation = unitOrientation;
        this.unitFixture = unitFixture;
        this.unitStatus = unitStatus;
        this.unitView = unitView;
        this.unitSubtype = unitSubtype;
        this.unitFloor = unitFloor;
        this.areaMasterDetail = areaMasterDetail;
        this.unitPaymentDetails = unitPaymentDetails;
    }
}
