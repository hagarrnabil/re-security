package com.sap.cloud.security.samples.resecurity.repositories;

import com.sap.cloud.security.samples.resecurity.model.PriceType;
import org.springframework.data.repository.CrudRepository;

public interface PriceTypeRepository extends CrudRepository<PriceType,Long> {
}
