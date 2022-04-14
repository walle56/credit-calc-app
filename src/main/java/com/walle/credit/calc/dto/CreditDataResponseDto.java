package com.walle.credit.calc.dto;

import lombok.Data;

@Data
public class CreditDataResponseDto {

    private long id;
    private double apartmentCost;
    private double percentage;
    private double userOwnPayment;
    private double monthlyPayment;
    private int years;
    private String bankName;
    private String bankIsin;

}
