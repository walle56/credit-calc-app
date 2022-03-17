package com.walle.credit.calc.controller;

import com.walle.credit.calc.dto.CreditDataInputDto;
import com.walle.credit.calc.dto.CreditDataResultDto;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * IMPORTANT - need to use 'import org.junit.jupiter.api.Test;' in the SpringBoot integration tests
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CalcControllerTest {

    private static final String HOST = "http://localhost:";
    private static final String PATH_CALC = "/calculate";
    private static final String PATH_LIST = "/calculate/list";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testCalculationWithIntegerNumbers() throws Exception {
        CreditDataInputDto dto = new CreditDataInputDto("700000", "2.24", "200000", 20);
        ResponseEntity<CreditDataResultDto> response = restTemplate.exchange(HOST + port + PATH_CALC,
                HttpMethod.PUT, new HttpEntity<>(dto), CreditDataResultDto.class);

        assertNotNull(response.getBody());
        assertEquals(700000.0, response.getBody().getApartmentCost());
        assertEquals(2586.64, response.getBody().getMonthlyPayment());
    }

    @Test
    public void testCalculationWithDecimalNumbers() throws Exception {
        CreditDataInputDto dto = new CreditDataInputDto("500000.50", "12.95", "100000.90", 30);
        ResponseEntity<CreditDataResultDto> response = restTemplate.exchange(HOST + port + PATH_CALC,
                HttpMethod.PUT, new HttpEntity<>(dto), CreditDataResultDto.class);

        assertNotNull(response.getBody());
        assertEquals(500000.50, response.getBody().getApartmentCost());
        assertEquals(4409.17, response.getBody().getMonthlyPayment());
    }
    
    @Test
    public void shouldFailOnConvertToNumber() throws Exception {
        CreditDataInputDto dto = new CreditDataInputDto("50string", "2", "10", 20);
        ResponseEntity<String> response = restTemplate.exchange(HOST + port + PATH_CALC,
                HttpMethod.PUT, new HttpEntity<>(dto), String.class);

        assertNotNull(response.getBody());
        assertTrue(response.getBody().contains("Bad Request"));
    }

    @Test
    public void testGetListOfAllCalculations() throws Exception {
        CreditDataInputDto dto1 = new CreditDataInputDto("7000.00", "22.95", "1000.10", 30);
        restTemplate.exchange(HOST + port + PATH_CALC,
                HttpMethod.PUT, new HttpEntity<>(dto1), CreditDataResultDto.class);
        CreditDataInputDto dto2 = new CreditDataInputDto("9000.00", "25.95", "3000.10", 20);
        restTemplate.exchange(HOST + port + PATH_CALC,
                HttpMethod.PUT, new HttpEntity<>(dto2), CreditDataResultDto.class);

        ResponseEntity<List<CreditDataResultDto>> response = restTemplate.exchange(HOST + port + PATH_LIST,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<CreditDataResultDto>>() {});

        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals(114.87, response.getBody().get(0).getMonthlyPayment());
        assertEquals(130.52, response.getBody().get(1).getMonthlyPayment());
    }
}
