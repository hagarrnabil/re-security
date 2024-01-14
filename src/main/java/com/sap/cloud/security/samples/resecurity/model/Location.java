package com.sap.cloud.security.samples.resecurity.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

@Entity
@Data
@EqualsAndHashCode(exclude = {"project"})
@Table(name = "location")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long locationCode;


    @Column(unique = true, length = 8, columnDefinition = "char(8)")
    @Length(max = 8)
    private String locationId;
//    @NotNull
    private String regionalLocation;

    @OneToOne(cascade = CascadeType.MERGE)
    private Project project;
    public Location() {
    }

    public Location(String locationId, String regionalLocation) {
        this.locationId = locationId;
        this.regionalLocation = regionalLocation;
    }

    public void setProject(Project project) {
        if (project != null) {
            this.project = project;
            project.setLocation(this);
        }
    }
}
