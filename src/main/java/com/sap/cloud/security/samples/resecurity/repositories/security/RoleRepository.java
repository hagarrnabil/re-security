package com.sap.cloud.security.samples.resecurity.repositories.security;

import com.sap.cloud.security.samples.resecurity.model.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
