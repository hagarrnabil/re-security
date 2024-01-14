package com.sap.cloud.security.samples.resecurity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(exclude = {"units", "project", "buildingType", "profitCenter"})
@Table(name = "building")
public class Building implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long buildingCode;


    @Column(unique = true, length = 8, columnDefinition = "char(8)")
    @Length(max = 8)
    private String buildingId;
//    @NotNull
    private String buildingDescription;
    @Column(length = 8, columnDefinition = "char(8)")
    @Length(max = 8)
    private String oldNumber;
    private LocalDate validFrom;
    private Integer numberOfFloors;
    private String profit;
    private Long profitCode;
    private Long projectCode;
    private Long buildingTypeCode;

    @ManyToOne
    private Project project;
    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "building")
    @JsonIgnore
    private Set<Unit> units = new HashSet<>();
    @ManyToOne
    private BuildingType buildingType;
    @ManyToOne
    private ProfitCenter profitCenter;

    public Building() {
    }

    public Building(String buildingId, String buildingDescription, String oldNumber, LocalDate validFrom, Integer numberOfFloors, String profit, Project project) {
        this.buildingId = buildingId;
        this.buildingDescription = buildingDescription;
        this.oldNumber = oldNumber;
        this.validFrom = validFrom;
        this.numberOfFloors = numberOfFloors;
        this.profit = profit;
        this.project = project;
    }

    public Building(String buildingId, String buildingDescription, String oldNumber, LocalDate validFrom, Integer numberOfFloors, String profit, Project project, Set<Unit> units, BuildingType buildingType, ProfitCenter profitCenter) {
        this.buildingId = buildingId;
        this.buildingDescription = buildingDescription;
        this.oldNumber = oldNumber;
        this.validFrom = validFrom;
        this.numberOfFloors = numberOfFloors;
        this.profit = profit;
        this.project = project;
        this.units = units;
        this.buildingType = buildingType;
        this.profitCenter = profitCenter;
    }

    public Building addUnit(Unit unit) {
        unit.setBuilding(this);
        this.units.add(unit);
        return this;
    }
}
