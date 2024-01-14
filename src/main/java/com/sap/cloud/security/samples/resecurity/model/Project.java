package com.sap.cloud.security.samples.resecurity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"company","profitCenter","buildings","location"})
@Entity
@Table(name = "project")
public class Project implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long projectCode;

    @Column(unique = true, length = 8, columnDefinition = "char(8)")
    @Length(max = 8)
    private String projectId;
//    @NotNull
    private String projectDescription;
    private LocalDate validFrom;
    private String profit;
    private Long companyCode;
    private Long profitCode;
    private Long locationCode;

    @ManyToOne
    private Company company;
    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "project")
    @JsonIgnore
    private Set<Building> buildings = new HashSet<>();

    @ManyToOne
    private ProfitCenter profitCenter;

    @OneToOne
    private Location location;

    public Project() {
    }

    public Project(String projectId, String projectDescription, LocalDate validFrom, String profit, Company company) {
        this.projectId = projectId;
        this.projectDescription = projectDescription;
        this.validFrom = validFrom;
        this.profit = profit;
        this.company = company;
    }

    public Project(String projectId, String projectDescription, LocalDate validFrom, String profit, Company company, Set<Building> buildings, ProfitCenter profitCenter, Location location) {
        this.projectId = projectId;
        this.projectDescription = projectDescription;
        this.validFrom = validFrom;
        this.profit = profit;
        this.company = company;
        this.buildings = buildings;
        this.profitCenter = profitCenter;
        this.location = location;
    }

    public Project addBuilding(Building building) {
        building.setProject(this);
        this.buildings.add(building);
        return this;
    }
}
