package com.walle.credit.calc.controller;

import com.walle.credit.calc.dto.BankDto;
import com.walle.credit.calc.mapper.BankMapper;
import com.walle.credit.calc.model.Bank;
import com.walle.credit.calc.service.BankService;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for manage Bank DTO
 * POST /banks - saves new Bank to DB
 * GET /banks - returns all available banks
 *
 * Guide for naming https://restfulapi.net/resource-naming/
 */
@RestController
public class BankController {

    private static final Logger LOG = LoggerFactory.getLogger(BankController.class);

    private final BankService bankService;
    private final BankMapper bankMapper;

    @Autowired
    public BankController(BankService bankService, BankMapper bankMapper) {
        this.bankService = bankService;
        this.bankMapper = bankMapper;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/banks",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public BankDto saveBank(@Valid @RequestBody BankDto bankDto) {
        LOG.info("Received POST request to /banks with data {}}", bankDto);

        Bank savedBank = bankService.save(bankMapper.toEntity(bankDto));
        return bankMapper.toDto(savedBank);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/banks",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BankDto> gerAllBanks() {
        LOG.info("Received GET request to /banks");
        return bankMapper.toDtoList(bankService.getAllBanks());
    }
}
