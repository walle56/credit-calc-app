package com.walle.credit.calc.controller;

import com.walle.credit.calc.dto.BankDto;
import com.walle.credit.calc.dto.CreditDataInputDto;
import com.walle.credit.calc.dto.CreditDataResponseDto;
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
 *
 * Guide for naming - https://restfulapi.net/resource-naming/
 * Explanation of the HTTP Methods usage - https://restfulapi.net/idempotent-rest-apis/
 */
@RestController
public class CreditController {

    private static final Logger LOG = LoggerFactory.getLogger(CreditController.class);

    private final BankService bankService;
    private final CreditService creditService;

    @Autowired
    public CreditController(BankService bankService, CreditService creditService) {
        this.bankService = bankService;
        this.creditService = creditService;
    }

    /*
     * Calculates credit data payments
     * PUT /credits/calculate
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/credits/calculate",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public CreditDataResponseDto calculatePayments(@Valid @RequestBody CreditDataInputDto creditDataInputDto) {
        LOG.info("Received PUT request to /credits/calculate with data {}", creditDataInputDto);

        CreditDataResponseDto creditDataResponseDto = creditService.calculatePayments(creditDataInputDto);
        setBankData(creditDataResponseDto);
        return creditDataResponseDto;
    }

    private void setBankData(CreditDataResponseDto responseDto) {
        Optional<BankDto> bankOpt = bankService.getBankByCreditOffer(responseDto.getPercentage());
        if (bankOpt.isPresent()) {
            BankDto bankDto = bankOpt.get();
            responseDto.setBankName(bankDto.getName());
            responseDto.setBankIsin(bankDto.getIsin());
        }
    }

    /*
     * Returns all available credit calculations
     * GET /credits
     */
    @RequestMapping(method = RequestMethod.GET, value = "/credits",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CreditDataResponseDto> getAllCreditsData() {
        LOG.info("Received GET request to /credits");
        return creditService.getAllCalculations();
    }

}
