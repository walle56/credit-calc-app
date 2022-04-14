package com.walle.credit.calc.repository;

import com.walle.credit.calc.model.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repository class for manage Bank entity
 */
public interface BankRepository extends JpaRepository<Bank, Long> {

    // VALUE(bto) means that creditOffers Map will be expanded and Spring JPA will look for the percentage in the expanded values
    @Query("FROM Bank b JOIN b.creditOffers bto WHERE :percentage in (VALUE(bto))")
    Bank getByCreditOfferPercentage(@Param("percentage") double percentage);
}
