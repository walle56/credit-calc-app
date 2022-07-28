package com.walle.credit.calc.controller;

import com.walle.credit.calc.dto.CreditDataInputDto;
import com.walle.credit.calc.dto.CreditDataResponseDto;
import java.util.List;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
class CreditControllerTest {

    private static final String HOST = "http://localhost:";
    private static final String PATH_CALC = "/credits/calculate";
    private static final String PATH_LIST = "/credits";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testCalculationWithIntegerNumbers() {
        CreditDataInputDto dto = new CreditDataInputDto("700000", "2.24", "200000", 20);
        ResponseEntity<CreditDataResponseDto> response = restTemplate.exchange(HOST + port + PATH_CALC,
                HttpMethod.PUT, new HttpEntity<>(dto), CreditDataResponseDto.class);

        assertNotNull(response.getBody());
        assertEquals(700000.0, response.getBody().getApartmentCost());
        assertEquals(2586.64, response.getBody().getMonthlyPayment());
    }

    @Test
    public void testCalculationWithDecimalNumbers() {
        CreditDataInputDto dto = new CreditDataInputDto("500000.50", "12.95", "100000.90", 30);
        ResponseEntity<CreditDataResponseDto> response = restTemplate.exchange(HOST + port + PATH_CALC,
                HttpMethod.PUT, new HttpEntity<>(dto), CreditDataResponseDto.class);

        assertNotNull(response.getBody());
        assertEquals(500000.50, response.getBody().getApartmentCost());
        assertEquals(4409.17, response.getBody().getMonthlyPayment());
    }
    
    @Test
    public void shouldFailOnConvertToNumber() {
        CreditDataInputDto dto = new CreditDataInputDto("50string", "2", "10", 20);
        ResponseEntity<String> response = restTemplate.exchange(HOST + port + PATH_CALC,
                HttpMethod.PUT, new HttpEntity<>(dto), String.class);

        assertNotNull(response.getBody());
        assertTrue(response.getBody().contains("Bad Request"));
    }

    @Test
    public void testGetListOfAllCalculations() {
        CreditDataInputDto dto1 = new CreditDataInputDto("7000.00", "22.95", "1000.10", 30);
        restTemplate.exchange(HOST + port + PATH_CALC,
                HttpMethod.PUT, new HttpEntity<>(dto1), CreditDataResponseDto.class);
        CreditDataInputDto dto2 = new CreditDataInputDto("9000.00", "25.95", "3000.10", 20);
        restTemplate.exchange(HOST + port + PATH_CALC,
                HttpMethod.PUT, new HttpEntity<>(dto2), CreditDataResponseDto.class);

        ResponseEntity<List<CreditDataResponseDto>> response = restTemplate.exchange(HOST + port + PATH_LIST,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<CreditDataResponseDto>>() {});

        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals(114.87, response.getBody().get(0).getMonthlyPayment());
        assertEquals(130.52, response.getBody().get(1).getMonthlyPayment());
    }
}
