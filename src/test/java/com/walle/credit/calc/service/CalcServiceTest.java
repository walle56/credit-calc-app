package com.walle.credit.calc.service;

import com.walle.credit.calc.model.CreditData;
import com.walle.credit.calc.model.CreditDataBuilder;
import com.walle.credit.calc.repository.CreditDataRepository;
import java.math.BigDecimal;
import java.util.List;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * IMPORTANT - need to use 'import org.junit.Test;' in the unit test classes with Mockito
 */
@RunWith(MockitoJUnitRunner.class)
public class CalcServiceTest {

    @Mock
    private CreditDataRepository repository;

    @InjectMocks
    private CalcService calcService;

    @Test
    public void testReadingCalculationsDataFromDB() {
        List<CreditData> repoResp = getListOfCreditData();
        when(repository.findAll()).thenReturn(repoResp);

        List<CreditData> serviceResp = calcService.getAllCalculations();

        assertEquals(repoResp.size(), serviceResp.size());
        assertTrue(repoResp.containsAll(serviceResp));
        assertTrue(serviceResp.containsAll(repoResp));
    }

    @Test
    public void testCalculatePayments() {
        CreditData creditData = new CreditDataBuilder()
                .setApartmentCost(BigDecimal.valueOf(500))
                .setPercentage(BigDecimal.valueOf(5.5))
                .setUserOwnPayment(BigDecimal.valueOf(100))
                .setYears(20).createCreditData();

        doNothing().when(repository.save(Mockito.any(CreditData.class)));

        CreditData serviceResp = calcService.calculatePayments(creditData);

        assertNotNull(serviceResp);
        assertEquals(BigDecimal.valueOf(123), serviceResp.getMonthlyPayment());
    }

    private List<CreditData> getListOfCreditData() {
        return List.of(
                new CreditDataBuilder().setId(1L).setApartmentCost(BigDecimal.valueOf(50))
                        .setPercentage(BigDecimal.valueOf(2.5))
                        .setUserOwnPayment(BigDecimal.valueOf(10))
                        .setMonthlyPayment(BigDecimal.valueOf(2.6))
                        .setYears(20).createCreditData(),
                new CreditDataBuilder().setId(2L).setApartmentCost(BigDecimal.valueOf(60))
                        .setPercentage(BigDecimal.valueOf(3.5))
                        .setUserOwnPayment(BigDecimal.valueOf(20))
                        .setMonthlyPayment(BigDecimal.valueOf(3.6))
                        .setYears(20).createCreditData(),
                new CreditDataBuilder().setId(3L).setApartmentCost(BigDecimal.valueOf(70))
                        .setPercentage(BigDecimal.valueOf(4.5))
                        .setUserOwnPayment(BigDecimal.valueOf(30))
                        .setMonthlyPayment(BigDecimal.valueOf(4.6))
                        .setYears(20).createCreditData());
    }
}
