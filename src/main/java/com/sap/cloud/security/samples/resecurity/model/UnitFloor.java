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
@EqualsAndHashCode(exclude = {"units"})
@Table(name = "unit_floor")
public class UnitFloor implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long unitFloorCode;


    @Column(unique = true, length = 8, columnDefinition = "char(8)")
    @Length(max = 8)
    private String floorId;
//    @NotNull
    private String floorDescr;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unitFloor")
    @JsonIgnore
    private Set<Unit> units = new HashSet<>();

    public UnitFloor() {
    }

    public UnitFloor(Long unitFloorCode, String floorId, String floorDescr, Set<Unit> units) {
        this.unitFloorCode = unitFloorCode;
        this.floorId = floorId;
        this.floorDescr = floorDescr;
        this.units = units;
    }

    public UnitFloor addUnit(Unit unit) {
        unit.setUnitFloor(this);
        this.units.add(unit);
        return this;
    }
}
