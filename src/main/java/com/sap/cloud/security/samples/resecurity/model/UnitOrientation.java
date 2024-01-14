package com.sap.cloud.security.samples.resecurity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(exclude = {"units"})
@Table(name = "unit_orientation")
public class UnitOrientation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long unitOrientationCode;
    @Column(unique = true, length = 8, columnDefinition = "char(8)")
    @Length(max = 8)
    private String orientationId;
//    @NotNull
    private String orientationDescr;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unitOrientation")
    @JsonIgnore
    private Set<Unit> units = new HashSet<>();

    public UnitOrientation() {
    }

    public UnitOrientation(Long unitOrientationCode, String orientationId, String orientationDescr, Set<Unit> units) {
        this.unitOrientationCode = unitOrientationCode;
        this.orientationId = orientationId;
        this.orientationDescr = orientationDescr;
        this.units = units;
    }

    public UnitOrientation addUnit(Unit unit) {
        unit.setUnitOrientation(this);
        this.units.add(unit);
        return this;
    }
}
