package com.sap.cloud.security.samples.resecurity.repositories;

import com.sap.cloud.security.samples.resecurity.model.Location;
import org.springframework.data.repository.CrudRepository;

public interface LocationRepository extends CrudRepository<Location,Long> {
}
