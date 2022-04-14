package com.walle.credit.calc.config;

import com.walle.credit.calc.repository.BankRepository;
import com.walle.credit.calc.repository.CreditDataRepository;
import com.walle.credit.calc.service.BankService;
import com.walle.credit.calc.service.CalcService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CalcConfig {

    @Bean
    public CalcService calcService(CreditDataRepository creditDataRepository) {
        return new CalcService(creditDataRepository);
    }

    @Bean
    public BankService bankService(BankRepository bankRepository) {
        return new BankService(bankRepository);
    }

}
