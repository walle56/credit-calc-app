package com.walle.credit.calc.repository;

import com.walle.credit.calc.model.CreditData;
import java.math.BigDecimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repository class to persist CreditData entity
 */
public interface CreditRepository extends JpaRepository<CreditData, Long> {

    @Query("FROM CreditData cd WHERE cd.monthlyPayment = :monthlyPayment")
    CreditData findByMonthlyPayment(@Param("monthlyPayment") BigDecimal monthlyPayment);

}
