package com.walle.credit.calc.dto;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankDto {

    private Long id;
    private String name;
    private String isin;
    private String headquarter;
    private Map<String, Double> creditProducts;

}
