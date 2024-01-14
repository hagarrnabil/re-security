package com.sap.cloud.security.samples.resecurity.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.Objects;

@Entity
@Data
@Table(name = "unit_of_measurement")
public class UnitOfMeasurement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long measurementCode;


    @Column(unique = true, length = 8, columnDefinition = "char(8)")
    @Length(max = 8)
    private String uomID;
//    @NotNull
    private String uomDescr;

    @OneToOne
    private AreaMasterDetail areaMasterDetail;

    public void setArea(AreaMasterDetail area) {
        if (area != null) {
            this.areaMasterDetail = area;
            area.setUnitOfMeasurement(this);
        }
    }
}
