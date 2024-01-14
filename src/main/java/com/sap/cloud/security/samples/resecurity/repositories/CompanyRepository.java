package com.sap.cloud.security.samples.resecurity.repositories;


import com.sap.cloud.security.samples.resecurity.commands.CompanyCommand;
import com.sap.cloud.security.samples.resecurity.model.Company;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CompanyRepository extends CrudRepository<Company,Long> {
    @Query("SELECT c FROM Company c WHERE CONCAT(c.companyCodeDescription, ' ', c.companyCodeId, ' ', c.id, ' ', c.projects) LIKE %?1%")
    public List<CompanyCommand> search(String keyword);
}
