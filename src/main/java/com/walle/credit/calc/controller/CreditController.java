package com.walle.credit.calc.controller;

import com.walle.credit.calc.dto.CreditDataInputDto;
import com.walle.credit.calc.dto.CreditDataResponseDto;
import com.walle.credit.calc.mapper.CreditDataMapper;
import com.walle.credit.calc.model.Bank;
import com.walle.credit.calc.model.CreditData;
import com.walle.credit.calc.service.BankService;
import com.walle.credit.calc.service.CreditService;
import java.util.List;
import java.util.Optional;
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
 * Controller for manage CreditData DTO
 * PUT /credits/calculate - to calculate credit data payments
 * GET /credits - returns all available credit calculations
 *
 * Guide for naming https://restfulapi.net/resource-naming/
 */
@RestController
public class CreditController {

    private static final Logger LOG = LoggerFactory.getLogger(CreditController.class);

    private final BankService bankService;
    private final CreditService creditService;
    private final CreditDataMapper creditDataMapper;

    @Autowired
    public CreditController(BankService bankService, CreditService creditService, CreditDataMapper creditDataMapper) {
        this.bankService = bankService;
        this.creditService = creditService;
        this.creditDataMapper = creditDataMapper;
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/credits/calculate",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public CreditDataResponseDto calculatePayments(@Valid @RequestBody CreditDataInputDto creditDataInputDto) {
        LOG.info("Received PUT request to /credits/calculate with data {}", creditDataInputDto);

        CreditData creditData = creditService.calculatePayments(creditDataMapper.toEntity(creditDataInputDto));
        CreditDataResponseDto responseDto = creditDataMapper.toDto(creditData);
        setBankData(responseDto);
        return responseDto;
    }

    private void setBankData(CreditDataResponseDto responseDto) {
        Optional<Bank> bankOpt = bankService.getBankByCreditOffer(responseDto.getPercentage());
        if (bankOpt.isPresent()) {
            Bank bank = bankOpt.get();
            responseDto.setBankName(bank.getName());
            responseDto.setBankIsin(bank.getIsin());
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/credits",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CreditDataResponseDto> getAllCreditsData() {
        LOG.info("Received GET request to /credits");

        List<CreditData> calculations = creditService.getAllCalculations();
        return creditDataMapper.toDtoList(calculations);
    }

}
