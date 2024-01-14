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
@Table(name = "usage_type")
public class UsageType implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long usageTypeCode;


    @Column(unique = true, length = 8, columnDefinition = "char(8)")
    @Length(max = 8)
    private String usageId;
//    @NotNull
    private String usageDescr;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usageType")
    @JsonIgnore
    private Set<Unit> units = new HashSet<>();

    public UsageType addUnit(Unit unit) {
        unit.setUsageType(this);
        this.units.add(unit);
        return this;
    }
}
