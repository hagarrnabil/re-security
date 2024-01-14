package com.sap.cloud.security.samples.resecurity.repositories;

import com.sap.cloud.security.samples.resecurity.model.Currency;
import org.springframework.data.repository.CrudRepository;

public interface CurrencyRepository extends CrudRepository<Currency,Long> {
}
