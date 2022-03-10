package com.walle.credit.calc.service;

import com.walle.credit.calc.entity.CreditData;
import com.walle.credit.calc.repository.CreditDataRepo;
import static com.walle.credit.calc.util.CalcConstants.ROUND_MODE;
import static com.walle.credit.calc.util.CalcConstants.SCALE_2;
import static com.walle.credit.calc.util.CalcConstants.SCALE_8;
import java.math.BigDecimal;

public class CalcService {

    private final CreditDataRepo creditDataRepo;

    public CalcService(CreditDataRepo creditDataRepo) {
        this.creditDataRepo = creditDataRepo;
    }

    public BigDecimal calculatePayments(CreditData creditData) {

        BigDecimal monthlyPercentage = creditData.getPercentage()
                .divide(BigDecimal.valueOf(12), SCALE_8, ROUND_MODE)
                .divide(BigDecimal.valueOf(100), SCALE_8, ROUND_MODE);

        // TODO refactor of using double
        double pow = Math.pow(BigDecimal.ONE.add(creditData.getPercentage()).doubleValue(),
                BigDecimal.ONE.subtract(BigDecimal.valueOf(creditData.getYears()).multiply(BigDecimal.valueOf(12))).doubleValue());

        BigDecimal divide = creditData.getPercentage().divide(BigDecimal.ONE.subtract(BigDecimal.valueOf(pow)), SCALE_2, ROUND_MODE);

        BigDecimal creditTotal = creditData.getApartmentCost().subtract(creditData.getUserOwnPayment());
        BigDecimal multiply = creditTotal.multiply(divide);

        return multiply;
    }
}
