package com.walle.credit.calc.config;

import com.walle.credit.calc.repository.BankRepository;
import com.walle.credit.calc.repository.CreditRepository;
import com.walle.credit.calc.service.BankService;
import com.walle.credit.calc.service.CreditService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CalcConfig {

    @Bean
    public CreditService calcService(CreditRepository creditRepository) {
        return new CreditService(creditRepository);
    }

    @Bean
    public BankService bankService(BankRepository bankRepository) {
        return new BankService(bankRepository);
    }

}
