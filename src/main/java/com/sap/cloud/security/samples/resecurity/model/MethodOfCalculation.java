package com.sap.cloud.security.samples.resecurity.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
@Data
@Entity
@Table(name = "method_of_calculation")
public class MethodOfCalculation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long calculationCode;
    @Column(unique = true, length = 8, columnDefinition = "char(8)", nullable = false)
    @Length(max = 8)
    private String mocID;
    @NotNull
    private String mocDescr;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "methodOfCalculation")
    private Set<UnitPaymentDetails> unitPaymentDetailsSet = new HashSet<>();

}
