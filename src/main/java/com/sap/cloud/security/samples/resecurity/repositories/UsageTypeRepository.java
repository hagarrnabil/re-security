package com.sap.cloud.security.samples.resecurity.repositories;

import com.sap.cloud.security.samples.resecurity.model.UsageType;
import org.springframework.data.repository.CrudRepository;

public interface UsageTypeRepository extends CrudRepository<UsageType,Long> {
}
