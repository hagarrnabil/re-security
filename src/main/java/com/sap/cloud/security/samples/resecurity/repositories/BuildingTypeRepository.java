package com.sap.cloud.security.samples.resecurity.repositories;

import com.sap.cloud.security.samples.resecurity.model.BuildingType;
import org.springframework.data.repository.CrudRepository;

public interface BuildingTypeRepository extends CrudRepository<BuildingType,Long> {
}
