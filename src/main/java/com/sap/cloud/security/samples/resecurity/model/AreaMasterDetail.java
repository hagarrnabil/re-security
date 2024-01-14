package com.sap.cloud.security.samples.resecurity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(exclude = {"units","projectArea","buildingArea","unitArea"})
@Table(name = "area_master_detail")
public class AreaMasterDetail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long areaCode;


    @Column(unique = true, length = 8, columnDefinition = "char(8)")
    @Length(max = 8)
    private String areaMaster;
//    @NotNull
    private String description;
    @Column(length = 1, columnDefinition = "char(1)")
    @Length(max = 1)
    private String projectFlag;
    @Column(length = 1, columnDefinition = "char(1)")
    @Length(max = 1)
    private String buildingFlag;
    @Column(length = 1, columnDefinition = "char(1)")
    @Length(max = 1)
    private String unitFlag;
    private Long projectAreaCode;
    private Long buildingAreaCode;
    private Long unitAreaCode;
    private Long measurementCode;

    @OneToOne
    private UnitOfMeasurement unitOfMeasurement;
    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "areaMasterDetail")
    @JsonIgnore
    private Set<Unit> units = new HashSet<>();
    @ManyToOne
    private ProjectArea projectArea;
    @ManyToOne
    private BuildingArea buildingArea;
    @ManyToOne
    private UnitArea unitArea;

    public AreaMasterDetail() {
    }

    public AreaMasterDetail(Long areaCode, String areaMaster, String description, String projectFlag, String buildingFlag, String unitFlag, UnitOfMeasurement unitOfMeasurement, Set<Unit> units, ProjectArea projectArea, BuildingArea buildingArea, UnitArea unitArea) {
        this.areaCode = areaCode;
        this.areaMaster = areaMaster;
        this.description = description;
        this.projectFlag = projectFlag;
        this.buildingFlag = buildingFlag;
        this.unitFlag = unitFlag;
        this.unitOfMeasurement = unitOfMeasurement;
        this.units = units;
        this.projectArea = projectArea;
        this.buildingArea = buildingArea;
        this.unitArea = unitArea;
    }

    public AreaMasterDetail addUnit(Unit unit) {
        unit.setAreaMasterDetail(this);
        this.units.add(unit);
        return this;
    }
}
