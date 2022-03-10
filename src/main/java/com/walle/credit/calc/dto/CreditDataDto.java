package com.walle.credit.calc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.walle.credit.calc.validator.StringToNumberConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditDataDto {

    @StringToNumberConstraint
    @JsonProperty("totalCost")
    private String apartmentCost;

    @StringToNumberConstraint
    private String percentage;

    @StringToNumberConstraint
    private String userOwnPayment;
    
    private int years;

}
