package com.sap.cloud.security.samples.resecurity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(exclude = {"units"})
@Table(name = "unit_view")
public class UnitView implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long unitViewCode;


    @Column(unique = true, length = 8, columnDefinition = "char(8)")
    @Length(max = 8)
    private String viewId;;
//    @NotNull
    private String viewDescr;;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unitView")
    @JsonIgnore
    private Set<Unit> units = new HashSet<>();

    public UnitView() {
    }

    public UnitView(Long unitViewCode, String viewId, String viewDescr, Set<Unit> units) {
        this.unitViewCode = unitViewCode;
        this.viewId = viewId;
        this.viewDescr = viewDescr;
        this.units = units;
    }

    public UnitView addUnit(Unit unit) {
        unit.setUnitView(this);
        this.units.add(unit);
        return this;
    }
}