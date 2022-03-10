package com.walle.credit.calc.controller;

import com.walle.credit.calc.dto.CreditDataDto;
import com.walle.credit.calc.entity.CreditData;
import com.walle.credit.calc.mapper.CreditDataMapper;
import com.walle.credit.calc.service.CalcService;
import java.math.BigDecimal;
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

@RestController
public class CalcController {

    private static final Logger LOG = LoggerFactory.getLogger(CalcController.class);

    private final CalcService calcService;
    private final CreditDataMapper creditDataMapper;

    @Autowired
    public CalcController(CalcService calcService, CreditDataMapper creditDataMapper) {
        this.calcService = calcService;
        this.creditDataMapper = creditDataMapper;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/calculate",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public BigDecimal calculatePayments(@Valid @RequestBody CreditDataDto creditDataDto) {
        LOG.info("Call to /calculate with data: " + creditDataDto.toString());
        CreditData creditData = creditDataMapper.toEntity(creditDataDto);
        return calcService.calculatePayments(creditData);
    }

}
