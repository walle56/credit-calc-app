package com.walle.credit.calc.config;

import com.walle.credit.calc.repository.CreditDataRepo;
import com.walle.credit.calc.service.CalcService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CalcConfig {

    @Bean
    public CalcService calcService(CreditDataRepo creditDataRepo) {
        return new CalcService(creditDataRepo);
    }

    @Bean
    public CreditDataRepo creditDataRepo() {
        return new CreditDataRepo();
    }

}
