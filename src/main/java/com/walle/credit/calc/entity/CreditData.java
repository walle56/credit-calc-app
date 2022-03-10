package com.walle.credit.calc.entity;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class CreditData {

    private BigDecimal apartmentCost;
    private BigDecimal percentage;
    private BigDecimal userOwnPayment;
    private int years;

}
