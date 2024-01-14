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
@Table(name = "unit_status")
public class UnitStatus implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long unitStatusCode;
    @Column(unique = true, length = 8, columnDefinition = "char(8)")
    @Length(max = 8)
    private String statusId;
//    @NotNull
    private String statusDescr;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unitStatus")
    @JsonIgnore
    private Set<Unit> units = new HashSet<>();

    public UnitStatus(Long unitStatusCode, String statusId, String statusDescr, Set<Unit> units) {
        this.unitStatusCode = unitStatusCode;
        this.statusId = statusId;
        this.statusDescr = statusDescr;
        this.units = units;
    }

    public UnitStatus() {
    }

    public UnitStatus addUnit(Unit unit) {
        unit.setUnitStatus(this);
        this.units.add(unit);
        return this;
    }
}
