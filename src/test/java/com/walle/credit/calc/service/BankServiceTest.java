package com.walle.credit.calc.service;

import com.walle.credit.calc.dto.BankDto;
import com.walle.credit.calc.mapper.BankMapper;
import com.walle.credit.calc.model.Bank;
import com.walle.credit.calc.repository.BankRepository;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

/**
 * IMPORTANT:
 * this test uses Jupiter (JUnit5) hence all annotations and dependencies should be used from:
 * import org.junit.jupiter.api.*
 * import org.junit.jupiter.api.Assertions.*
 */
@ExtendWith(MockitoExtension.class)
public class BankServiceTest {

    @Mock
    private BankMapper bankMapper;

    @Mock
    private BankRepository bankRepository;

    @InjectMocks
    private BankService bankService;

    @Test
    public void testSaveBank() {
        Bank bankRequest = new Bank(null, "Name1", "112233B", "City", Map.of("Offer1", 5.67));
        BankDto bankRequestDto = BankMapper.INSTANCE.toDto(bankRequest);
        Bank bankResponse = new Bank(1L, "Name1", "112233B", "City", Map.of("Offer1", 5.67));
        BankDto bankResponseDto = BankMapper.INSTANCE.toDto(bankResponse);

        when(bankMapper.toEntity(eq(bankRequestDto))).thenReturn(bankRequest);
        when(bankRepository.save(eq(bankRequest))).thenReturn(bankResponse);
        when(bankMapper.toDto(eq(bankResponse))).thenReturn(bankResponseDto);

        BankDto serviceResponse = bankService.save(bankRequestDto);
        assertNotNull(serviceResponse);
        assertEquals(bankResponseDto, serviceResponse);
    }

    @Test
    public void testGetAllBanks() {
        Bank bank1 = new Bank(1L, "Name2", "11223311B", "City1", Map.of("Offer11", 5.67));
        BankDto bank1Dto = BankMapper.INSTANCE.toDto(bank1);
        Bank bank2 = new Bank(2L, "Name2", "11223322B", "City2", Map.of("Offer12", 6.7));
        BankDto bank2Dto = BankMapper.INSTANCE.toDto(bank2);

        List<Bank> banksList = List.of(bank1, bank2);
        List<BankDto> banksDtoList = List.of(bank1Dto, bank2Dto);

        when(bankRepository.findAll()).thenReturn(banksList);
        when(bankMapper.toDtoList(eq(banksList))).thenReturn(banksDtoList);

        List<BankDto> serviceResponse = bankService.getAllBanks();

        assertNotNull(serviceResponse);
        assertEquals(2, serviceResponse.size());
        assertTrue(serviceResponse.containsAll(banksDtoList));
        assertTrue(banksDtoList.containsAll(serviceResponse));
    }

    @Test
    public void testGetBankByCreditOffer() {
        double percentage = 5.67;
        Bank bankResponse = new Bank(1L, "Name1", "112233B", "City", Map.of("Offer1", 5.67));
        BankDto bankResponseDto = BankMapper.INSTANCE.toDto(bankResponse);

        when(bankRepository.getByCreditOfferPercentage(eq(percentage))).thenReturn(bankResponse);
        when(bankMapper.toDto(bankResponse)).thenReturn(bankResponseDto);

        Optional<BankDto> serviceResponse = bankService.getBankByCreditOffer(percentage);
        assertTrue(serviceResponse.isPresent());
        assertEquals(bankResponseDto, serviceResponse.get());
    }

    @Test
    public void testGetBankByCreditOfferRepoReturnsNull() {
        double percentage = 8.7;
        when(bankRepository.getByCreditOfferPercentage(eq(percentage))).thenReturn(null);

        Optional<BankDto> serviceResponse = bankService.getBankByCreditOffer(percentage);
        assertTrue(serviceResponse.isEmpty());
    }
}
