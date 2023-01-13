package com.walle.credit.calc.config;

import com.walle.credit.calc.mapper.BankMapper;
import com.walle.credit.calc.mapper.CreditDataMapper;
import com.walle.credit.calc.repository.BankRepository;
import com.walle.credit.calc.repository.CreditRepository;
import com.walle.credit.calc.service.BankService;
import com.walle.credit.calc.service.CreditService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CalcConfig {

    @Bean
    public CreditService calcService(CreditDataMapper creditDataMapper, CreditRepository creditRepository) {
        return new CreditService(creditDataMapper, creditRepository);
    }

    @Bean
    public BankService bankService(BankMapper bankMapper, BankRepository bankRepository) {
        return new BankService(bankMapper, bankRepository);
    }

}
