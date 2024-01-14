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
@Table(name = "unit_subtypes")
public class UnitSubtype implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long unitSubtypeCode;


    @Column(unique = true, length = 8, columnDefinition = "char(8)")
    @Length(max = 8)
    private String subtypeId;
//    @NotNull
    private String subtypeDescr;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unitSubtype")
    @JsonIgnore
    private Set<Unit> units = new HashSet<>();

    public UnitSubtype() {
    }

    public UnitSubtype(Long unitSubtypeCode, String subtypeId, String subtypeDescr, Set<Unit> units) {
        this.unitSubtypeCode = unitSubtypeCode;
        this.subtypeId = subtypeId;
        this.subtypeDescr = subtypeDescr;
        this.units = units;
    }

    public UnitSubtype addUnit(Unit unit) {
        unit.setUnitSubtype(this);
        this.units.add(unit);
        return this;
    }
}
