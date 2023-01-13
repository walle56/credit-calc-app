package com.walle.credit.calc.service;

import com.walle.credit.calc.dto.BankDto;
import com.walle.credit.calc.mapper.BankMapper;
import com.walle.credit.calc.model.Bank;
import com.walle.credit.calc.repository.BankRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  Class to manage Bank entity,
 *  convert it to/from DTO and sent to the repository
 */
public class BankService {

    private static final Logger LOG = LoggerFactory.getLogger(BankService.class);

    private final BankMapper bankMapper;
    private final BankRepository bankRepository;

    public BankService(BankMapper bankMapper, BankRepository bankRepository) {
        this.bankMapper = bankMapper;
        this.bankRepository = bankRepository;
    }

    public BankDto save(BankDto bankDto) {
        Bank bankToSave = bankMapper.toEntity(bankDto);
        Bank bankPersisted = bankRepository.save(bankToSave);
        return bankMapper.toDto(bankPersisted);
    }

    public List<BankDto> getAllBanks() {
        List<Bank> banks = bankRepository.findAll();
        // TODO check why creditOffers are empty
        return bankMapper.toDtoList(banks);
    }

    public Optional<BankDto> getBankByCreditOffer(double percentage) {
        Bank bank = bankRepository.getByCreditOfferPercentage(percentage);
        if (bank == null) {
            return Optional.empty();
        }
        return Optional.of(bankMapper.toDto(bank));
    }
}
