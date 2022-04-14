package com.walle.credit.calc.service;

import com.walle.credit.calc.model.Bank;
import com.walle.credit.calc.repository.BankRepository;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.eq;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BankServiceTest {

    @Mock
    private BankRepository bankRepository;

    @InjectMocks
    private BankService bankService;

    @Test
    public void testSaveBank() {
        Bank bankReq = new Bank(null, "Name1", "112233B", "City", Map.of("Offer1", 5.67));
        Bank repoResp = new Bank(1L, "Name1", "112233B", "City", Map.of("Offer1", 5.67));

        when(bankRepository.save(eq(bankReq))).thenReturn(repoResp);

        Bank serviceResp = bankService.save(bankReq);
        assertNotNull(serviceResp);
        assertEquals(repoResp, serviceResp);
    }

    @Test
    public void testGetAllBanks() {
        List<Bank> repoResp = List.of(
                new Bank(1L, "Name2", "11223311B", "City1", Map.of("Offer11", 5.67)),
                new Bank(2L, "Name2", "11223322B", "City2", Map.of("Offer12", 6.7)));

        when(bankRepository.findAll()).thenReturn(repoResp);

        List<Bank> serviceResp = bankService.getAllBanks();
        assertNotNull(serviceResp);
        assertEquals(2, serviceResp.size());
        assertTrue(serviceResp.containsAll(repoResp));
        assertTrue(repoResp.containsAll(serviceResp));
    }

    @Test
    public void testGetBankByCreditOffer() {
        double percentage = 5.67;
        Bank repoResp = new Bank(1L, "Name1", "112233B", "City", Map.of("Offer1", 5.67));

        when(bankRepository.getByCreditOfferPercentage(eq(percentage))).thenReturn(repoResp);

        Optional<Bank> serviceResp = bankService.getBankByCreditOffer(percentage);
        assertTrue(serviceResp.isPresent());
        assertEquals(repoResp, serviceResp.get());
    }

    @Test
    public void testGetBankByCreditOfferRepoReturnsNull() {
        double percentage = 8.7;
        when(bankRepository.getByCreditOfferPercentage(eq(percentage))).thenReturn(null);

        Optional<Bank> serviceResp = bankService.getBankByCreditOffer(percentage);
        assertTrue(serviceResp.isEmpty());
    }
}
