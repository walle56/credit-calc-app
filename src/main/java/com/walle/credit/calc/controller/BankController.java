package com.walle.credit.calc.controller;

import com.walle.credit.calc.dto.BankDto;
import com.walle.credit.calc.service.BankService;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for manage Bank DTO
 *
 * Guide for naming - https://restfulapi.net/resource-naming/
 */
@RestController
public class BankController {

    private static final Logger LOG = LoggerFactory.getLogger(BankController.class);

    private final BankService bankService;

    @Autowired
    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    /*
     * Saves new Bank to the DB.
     * POST /banks
     */
    @RequestMapping(method = RequestMethod.POST, value = "/banks",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public BankDto saveBank(@Valid @RequestBody BankDto bankDto) {
        LOG.info("Received POST request to /banks with data {}}", bankDto);
        return bankService.save(bankDto);
    }

    /*
     * Returns all available banks.
     * GET /banks
     */
    @RequestMapping(method = RequestMethod.GET, value = "/banks",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BankDto> gerAllBanks() {
        LOG.info("Received GET request to /banks");
        return bankService.getAllBanks();
    }
}
