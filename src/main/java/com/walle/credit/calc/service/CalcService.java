package com.walle.credit.calc.service;

import com.walle.credit.calc.entity.CreditData;
import com.walle.credit.calc.repository.CreditDataRepository;
import static com.walle.credit.calc.util.CalcConstants.ROUND_MODE;
import static com.walle.credit.calc.util.CalcConstants.SCALE_2;
import static com.walle.credit.calc.util.CalcConstants.SCALE_8;
import java.math.BigDecimal;
import java.math.MathContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class CalcService {

    private static final Logger LOG = LoggerFactory.getLogger(CalcService.class);

    private final CreditDataRepository creditDataRepository;

    @Autowired
    public CalcService(CreditDataRepository creditDataRepository) {
        this.creditDataRepository = creditDataRepository;
    }

    public CreditData calculatePayments(CreditData creditData) {
        try {
            // calculate monthly percentage:
            // monthlyPercentage = creditData.percentage / 12 / 100
            BigDecimal monthlyPercentage = creditData.getPercentage()
                    .divide(BigDecimal.valueOf(12), SCALE_8, ROUND_MODE)
                    .divide(BigDecimal.valueOf(100), SCALE_8, ROUND_MODE);
            System.out.println(monthlyPercentage);

            // calculate power of monthlyPercentage to total months:
            // percentagePowMonths = (1 + monthlyPercentage) power of (creditData.years * 12)
            BigDecimal percentagePowMonths = BigDecimal.ONE.add(monthlyPercentage)
                    .pow(-(creditData.getYears() * 12), new MathContext(SCALE_8))
                    .setScale(SCALE_8, ROUND_MODE);
            System.out.println(percentagePowMonths);

            // calculate right part of the formula:
            // rightValue = monthlyPercentage / (1 - percentagePowMonths)
            BigDecimal rightValue = monthlyPercentage
                    .divide(BigDecimal.ONE
                            .subtract(percentagePowMonths), SCALE_8, ROUND_MODE);
            System.out.println(rightValue);

            // result = (creditData.apartmentCost - creditData.userOwnPayment) * rightValue
            BigDecimal result = rightValue.multiply(creditData.getApartmentCost()
                            .subtract(creditData.getUserOwnPayment()))
                    .setScale(SCALE_2, ROUND_MODE);
            System.out.println(result);

            creditData.setMonthlyPayment(result);

        } catch (Exception e) {
            LOG.error("Error while calculating the credit data.", e);
            throw e;
        }

        creditDataRepository.save(creditData);
        return creditData;
    }

    public Iterable<CreditData> getAllCalculations() {
        return creditDataRepository.findAll();
    }
}
