package com.walle.credit.calc.service;

import com.walle.credit.calc.entity.CreditData;
import com.walle.credit.calc.entity.CreditDataBuilder;
import com.walle.credit.calc.repository.CreditDataRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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
        List<CreditData> repoData = getListOfCreditData();
        Mockito.when(repository.findAll()).thenReturn(repoData);

        Iterable<CreditData> resultIter = calcService.getAllCalculations();

        List<CreditData> resultList = new ArrayList<>();
        resultIter.forEach(resultList::add);
        assertEquals(repoData.size(), resultList.size());
        assertTrue(repoData.containsAll(resultList));
        assertTrue(resultList.containsAll(repoData));
    }

    @Test
    public void testCalculatePayments() {
        CreditData creditData = new CreditDataBuilder()
                .setApartmentCost(BigDecimal.valueOf(500))
                .setPercentage(BigDecimal.valueOf(5.5))
                .setUserOwnPayment(BigDecimal.valueOf(100))
                .setYears(20).createCreditData();
        Mockito.doNothing().when(repository.save(Mockito.any(CreditData.class)));

        CreditData result = calcService.calculatePayments(creditData);

        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(123), result.getMonthlyPayment());
    }

    private List<CreditData> getListOfCreditData() {
        return List.of(
                new CreditDataBuilder().setId(1L).setApartmentCost(BigDecimal.valueOf(50)).setPercentage(BigDecimal.valueOf(2.5)).setUserOwnPayment(BigDecimal.valueOf(10)).setMonthlyPayment(BigDecimal.valueOf(2.6)).setYears(20).createCreditData(),
                new CreditDataBuilder().setId(2L).setApartmentCost(BigDecimal.valueOf(60)).setPercentage(BigDecimal.valueOf(3.5)).setUserOwnPayment(BigDecimal.valueOf(20)).setMonthlyPayment(BigDecimal.valueOf(3.6)).setYears(20).createCreditData(),
                new CreditDataBuilder().setId(3L).setApartmentCost(BigDecimal.valueOf(70)).setPercentage(BigDecimal.valueOf(4.5)).setUserOwnPayment(BigDecimal.valueOf(30)).setMonthlyPayment(BigDecimal.valueOf(4.6)).setYears(20).createCreditData());
    }
}
