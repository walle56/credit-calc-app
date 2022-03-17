package com.walle.credit.calc.entity;

import java.math.BigDecimal;

public class CreditDataBuilder {
    
    private Long id;
    private BigDecimal apartmentCost;
    private BigDecimal percentage;
    private BigDecimal userOwnPayment;
    private BigDecimal monthlyPayment;
    private int years;

    public CreditData createCreditData() {
        return new CreditData(id, apartmentCost, percentage, userOwnPayment, monthlyPayment, years);
    }

    public CreditDataBuilder setApartmentCost(BigDecimal apartmentCost) {
        this.apartmentCost = apartmentCost;
        return this;
    }

    public CreditDataBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public CreditDataBuilder setMonthlyPayment(BigDecimal monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
        return this;
    }

    public CreditDataBuilder setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
        return this;
    }

    public CreditDataBuilder setUserOwnPayment(BigDecimal userOwnPayment) {
        this.userOwnPayment = userOwnPayment;
        return this;
    }

    public CreditDataBuilder setYears(int years) {
        this.years = years;
        return this;
    }
}
