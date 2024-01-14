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
@Table(name = "currency")
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long currencyCode;
    @Column(unique = true, length = 8, columnDefinition = "char(8)", nullable = false)
    @Length(max = 8)
    private String currencyID;
    @NotNull
    private String currencyDescr;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "currency")
    private Set<UnitPaymentDetails> unitPaymentDetailsSet = new HashSet<>();

}
