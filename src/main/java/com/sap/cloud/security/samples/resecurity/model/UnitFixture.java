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
@EqualsAndHashCode(exclude = {"units"})
@Table(name = "unit_fixture")
public class UnitFixture implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long unitFixtureCode;


    @Column(unique = true, length = 8, columnDefinition = "char(8)")
    @Length(max = 8)
    private String fixtureId;
//    @NotNull
    private String fixtureDescr;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unitFixture")
    @JsonIgnore
    private Set<Unit> units = new HashSet<>();

    public UnitFixture addUnit(Unit unit) {
        unit.setUnitFixture(this);
        this.units.add(unit);
        return this;
    }

    public UnitFixture() {
    }

    public UnitFixture(Long unitFixtureCode, String fixtureId, String fixtureDescr, Set<Unit> units) {
        this.unitFixtureCode = unitFixtureCode;
        this.fixtureId = fixtureId;
        this.fixtureDescr = fixtureDescr;
        this.units = units;
    }
}
