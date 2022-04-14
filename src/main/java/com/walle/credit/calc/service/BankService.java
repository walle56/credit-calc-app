package com.walle.credit.calc.service;

import com.walle.credit.calc.model.Bank;
import com.walle.credit.calc.repository.BankRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BankService {

    private static final Logger LOG = LoggerFactory.getLogger(BankService.class);

    private final BankRepository bankRepository;

    public BankService(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    public Bank save(Bank bankToSave) {
        // TODO add validation and proper save
        return bankRepository.save(bankToSave);
    }

    public List<Bank> getAllBanks() {
        List<Bank> banks = bankRepository.findAll();
        // TODO check why creditOffers are empty
        return banks;
    }

    public Optional<Bank> getBankByCreditOffer(double percentage) {
        Bank bank = bankRepository.getByCreditOfferPercentage(percentage);
        return Optional.ofNullable(bank);
    }
}
