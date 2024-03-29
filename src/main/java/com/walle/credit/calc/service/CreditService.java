package com.walle.credit.calc.service;

import com.walle.credit.calc.dto.CreditDataInputDto;
import com.walle.credit.calc.dto.CreditDataResponseDto;
import com.walle.credit.calc.mapper.CreditDataMapper;
import com.walle.credit.calc.model.CreditData;
import com.walle.credit.calc.repository.CreditRepository;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static com.walle.credit.calc.util.CalcConstants.ROUND_MODE;
import static com.walle.credit.calc.util.CalcConstants.SCALE_2;
import static com.walle.credit.calc.util.CalcConstants.SCALE_8;

/**
 *  Class responsible for CreditData calculations
 */
public class CreditService {

    private static final Logger LOG = LoggerFactory.getLogger(CreditService.class);

    private final CreditDataMapper creditDataMapper;
    private final CreditRepository creditRepository;

    public CreditService(CreditDataMapper creditDataMapper, CreditRepository creditRepository) {
        this.creditDataMapper = creditDataMapper;
        this.creditRepository = creditRepository;
    }

    /**
     * Calculates monthly payment
     *
     * @param creditDataInputDto input credit parameters
     * @return CreditData with monthly payment
     */
    public CreditDataResponseDto calculatePayments(CreditDataInputDto creditDataInputDto) {
        CreditData creditData = creditDataMapper.toEntity(creditDataInputDto);

        try {
            // calculate monthly percentage:
            // monthlyPercentage = creditData.percentage / 12 / 100
            BigDecimal monthlyPercentage = creditData.getPercentage()
                    .divide(BigDecimal.valueOf(12), SCALE_8, ROUND_MODE)
                    .divide(BigDecimal.valueOf(100), SCALE_8, ROUND_MODE);

            // calculate power of monthlyPercentage to total months:
            // percentagePowMonths = (1 + monthlyPercentage) power of (creditData.years * 12)
            BigDecimal percentagePowMonths = BigDecimal.ONE.add(monthlyPercentage)
                    .pow(-(creditData.getYears() * 12), new MathContext(SCALE_8))
                    .setScale(SCALE_8, ROUND_MODE);

            // calculate right part of the formula:
            // rightValue = monthlyPercentage / (1 - percentagePowMonths)
            BigDecimal rightValue = monthlyPercentage
                    .divide(BigDecimal.ONE
                            .subtract(percentagePowMonths), SCALE_8, ROUND_MODE);

            // result = (creditData.apartmentCost - creditData.userOwnPayment) * rightValue
            BigDecimal result = rightValue.multiply(creditData.getApartmentCost()
                            .subtract(creditData.getUserOwnPayment()))
                    .setScale(SCALE_2, ROUND_MODE);

            creditData.setMonthlyPayment(result);
            LOG.debug("Calculated credit data: {}", creditData.toString());

        } catch (Exception e) {
            LOG.error("Error while calculating the credit data.", e);
            throw e;
        }

        CreditData creditDataPersisted = creditRepository.save(creditData);
        return creditDataMapper.toResponseDto(creditDataPersisted);
    }

    /**
     * Method to read all credit calculation
     * @return all credit calculation from the DB
     */
    public List<CreditDataResponseDto> getAllCalculations() {
        List<CreditData> creditDataIter = creditRepository.findAll();
        LOG.trace("List of the CreditData entities {}", creditDataIter);
        return creditDataMapper.toDtoList(creditDataIter);
    }
}
