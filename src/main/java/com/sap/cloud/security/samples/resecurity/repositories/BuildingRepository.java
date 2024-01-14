package com.sap.cloud.security.samples.resecurity.repositories;

import com.sap.cloud.security.samples.resecurity.model.Building;
import org.springframework.data.repository.CrudRepository;

public interface BuildingRepository extends CrudRepository<Building,Long> {
}
