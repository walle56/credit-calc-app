package com.walle.credit.calc.entity;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CreditData {

    private @Id @GeneratedValue Long Id;
    private BigDecimal apartmentCost;
    private BigDecimal percentage;
    private BigDecimal userOwnPayment;
    private BigDecimal monthlyPayment;
    private int years;

}
