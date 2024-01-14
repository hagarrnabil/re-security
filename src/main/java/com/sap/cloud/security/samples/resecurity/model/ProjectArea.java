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
@EqualsAndHashCode(exclude = {"areaMasterDetails"})
@Table(name = "project_area")
public class ProjectArea implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long projectAreaCode;


    @Column(unique = true, length = 8, columnDefinition = "char(8)")
    @Length(max = 8)
    private String projectArea;
//    @NotNull
    private String description;

    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "projectArea")
    @JsonIgnore
    private Set<AreaMasterDetail> areaMasterDetails = new HashSet<>();

    public ProjectArea addAMD(AreaMasterDetail areaMasterDetail) {
        areaMasterDetail.setProjectArea(this);
        this.areaMasterDetails.add(areaMasterDetail);
        return this;
    }
}
