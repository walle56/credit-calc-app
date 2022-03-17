package com.walle.credit.calc.controller;

import com.walle.credit.calc.dto.CreditDataInputDto;
import com.walle.credit.calc.dto.CreditDataResultDto;
import com.walle.credit.calc.model.CreditData;
import com.walle.credit.calc.mapper.CreditDataMapper;
import com.walle.credit.calc.service.CalcService;
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

    @RequestMapping(method = RequestMethod.PUT, value = "/calculate",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public CreditDataResultDto calculatePayments(@Valid @RequestBody CreditDataInputDto creditDataInputDto) {
        LOG.info("Call to /calculate with data: " + creditDataInputDto.toString());
        return creditDataMapper.toDto(
                calcService.calculatePayments(
                        creditDataMapper.toEntity(creditDataInputDto)));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/calculate/list",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CreditDataResultDto> getAllCalculations() {
        LOG.info("Call to /calculate/list");
        Iterable<CreditData> allCalculations = calcService.getAllCalculations();
        return creditDataMapper.toDto(allCalculations);
    }

}
