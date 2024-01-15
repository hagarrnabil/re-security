package com.sap.cloud.security.samples.resecurity.model.security;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String username;
    private String password;

    @Singular
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "user_roles",
    joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
    inverseJoinColumns = {@JoinColumn(name = "AUTHORITY_ID", referencedColumnName = "ID")})
    private Set<Authority> authorities;

    @Builder.Default
    private Boolean accountNonExpired = true;
    @Builder.Default
    private Boolean accountNonLocked = true;
    @Builder.Default
    private Boolean credentialsNonExpired = true;
    @Builder.Default
    private Boolean enabled = true;

}