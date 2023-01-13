package com.walle.credit.calc.service;

import com.walle.credit.calc.dto.CreditDataInputDto;
import com.walle.credit.calc.dto.CreditDataResponseDto;
import com.walle.credit.calc.mapper.CreditDataMapper;
import com.walle.credit.calc.model.CreditData;
import com.walle.credit.calc.model.CreditDataBuilder;
import com.walle.credit.calc.repository.CreditRepository;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

/**
 * IMPORTANT:
 * this test uses Jupiter (JUnit5) hence all annotations and dependencies should be used from:
 * import org.junit.jupiter.api.*
 * import org.junit.jupiter.api.Assertions.*
 */
@ExtendWith(MockitoExtension.class)
public class CreditServiceTest {

    @Mock
    private CreditDataMapper creditDataMapper;

    @Mock
    private CreditRepository repository;

    @InjectMocks
    private CreditService creditService;

    @Test
    public void testReadingCalculationsDataFromDB() {
        List<CreditData> creditDataList = getListOfCreditData();
        List<CreditDataResponseDto> creditDataDtoList = CreditDataMapper.INSTANCE.toDtoList(creditDataList);

        when(repository.findAll()).thenReturn(creditDataList);
        when(creditDataMapper.toDtoList(eq(creditDataList))).thenReturn(creditDataDtoList);


        List<CreditDataResponseDto> serviceResponse = creditService.getAllCalculations();

        assertEquals(creditDataList.size(), serviceResponse.size());
        assertTrue(creditDataDtoList.containsAll(serviceResponse));
        assertTrue(serviceResponse.containsAll(creditDataDtoList));
    }

    @Test
    public void testCalculatePayments() {
        CreditData creditData = new CreditDataBuilder()
                .setApartmentCost(BigDecimal.valueOf(500))
                .setPercentage(BigDecimal.valueOf(5.5))
                .setUserOwnPayment(BigDecimal.valueOf(100))
                .setYears(20).createCreditData();
        CreditDataInputDto creditDataInputDto = CreditDataMapper.INSTANCE.toInputDto(creditData);
        CreditDataResponseDto creditDataResponseDto = CreditDataMapper.INSTANCE.toResponseDto(creditData);

        when(creditDataMapper.toEntity(any(CreditDataInputDto.class))).thenReturn(creditData);
        when(repository.save(any(CreditData.class))).thenReturn(creditData);
        when(creditDataMapper.toResponseDto(creditData)).thenReturn(creditDataResponseDto);

        CreditDataResponseDto serviceResponse = creditService.calculatePayments(creditDataInputDto);

        assertNotNull(serviceResponse);
        assertEquals(0.0, serviceResponse.getMonthlyPayment());
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
