package com.sap.cloud.security.samples.resecurity.model;

import jakarta.persistence.*;
import org.hibernate.validator.constraints.Length;

import java.util.HashSet;
import java.util.Set;

@Entity
public class UnitPaymentDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true, length = 8, columnDefinition = "char(8)", nullable = false)
    @Length(max = 8)
    private String priceID;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unitPaymentDetails")
    private Set<Unit> units = new HashSet<>();
    @ManyToOne
    private Currency currency;
    @ManyToOne
    private MethodOfCalculation methodOfCalculation;
}
