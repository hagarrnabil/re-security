package com.sap.cloud.security.samples.resecurity.repositories.security;

import com.sap.cloud.security.samples.resecurity.model.security.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
}