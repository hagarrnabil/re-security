package com.sap.cloud.security.samples.resecurity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(exclude = {"buildings"})
@Table(name = "building_types")
public class BuildingType implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long buildingTypeCode;


    @Column(unique = true, length = 8, columnDefinition = "char(8)")
    @Length(max = 8)
    private String buildingTypeId;
    //    @NotNull
    private String buildingTypeDescr;

    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "buildingType")
    @JsonIgnore
    private Set<Building> buildings = new HashSet<>();

    public BuildingType addBuilding(Building building) {
        building.setBuildingType(this);
        this.buildings.add(building);
        return this;
    }
}
