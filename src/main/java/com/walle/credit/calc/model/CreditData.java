package com.walle.credit.calc.model;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "credit_data")
public class CreditData {
    @Id
    @GeneratedValue
    private Long Id;

    private BigDecimal apartmentCost;
    private BigDecimal percentage;
    private BigDecimal userOwnPayment;
    private BigDecimal monthlyPayment;
    private int years;

}
