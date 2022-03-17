package com.walle.credit.calc.dto;

import lombok.Data;

@Data
public class CreditDataResultDto {

    private long id;
    private double apartmentCost;
    private double percentage;
    private double userOwnPayment;
    private double monthlyPayment;
    private int years;

}
