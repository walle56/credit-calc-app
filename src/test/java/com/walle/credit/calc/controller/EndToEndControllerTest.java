package com.walle.credit.calc.controller;

import com.walle.credit.calc.dto.BankDto;
import com.walle.credit.calc.dto.CreditDataInputDto;
import com.walle.credit.calc.dto.CreditDataResponseDto;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * IMPORTANT:
 * this test uses Jupiter (JUnit5) hence all annotations and dependencies should be used from:
 * import org.junit.jupiter.api.*
 * import org.junit.jupiter.api.Assertions.*
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// @DirtiesContext tells Spring to clean up DB before this test class execution
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class EndToEndControllerTest {

    private static final String HOST = "http://localhost:";
    private static final String PATH_CALC = "/credits/calculate";
    private static final String PATH_BANK = "/banks";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGetBankByCreditPercentage() {
        // save Bank1
        BankDto bankDto1 = new BankDto(null, "bank1", "00112233B", "Here", Map.of("Offer1", 3.5, "Offer2", 2.25));
        restTemplate.exchange(HOST + port + PATH_BANK,
                HttpMethod.POST, new HttpEntity<>(bankDto1), BankDto.class);

        // save Bank2
        BankDto bankDto2 = new BankDto(null, "bank2", "223344B", "There", Map.of("Offer1", 5.5, "Offer2", 6.75));
        restTemplate.exchange(HOST + port + PATH_BANK,
                HttpMethod.POST, new HttpEntity<>(bankDto2), BankDto.class);

        ResponseEntity<List<BankDto>> response1 = restTemplate.exchange(HOST + port + PATH_BANK,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<BankDto>>() {});

        System.out.println(response1.getBody());

        // calculate credit date
        CreditDataInputDto dto = new CreditDataInputDto("7000", "5.5", "20000", 20);
        ResponseEntity<CreditDataResponseDto> response = restTemplate.exchange(HOST + port + PATH_CALC,
                HttpMethod.PUT, new HttpEntity<>(dto), CreditDataResponseDto.class);

        assertNotNull(response.getBody());
        System.out.println(response.getBody());
    }
}
