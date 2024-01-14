package com.sap.cloud.security.samples.resecurity.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.Objects;
@Data
@Entity
@Table(name = "price_type")
public class PriceType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long priceTypeCode;
    @Column(unique = true, length = 8, columnDefinition = "char(8)", nullable = false)
    @Length(max = 8)
    private String priceID;
    @NotNull
    private String priceDescr;

}
