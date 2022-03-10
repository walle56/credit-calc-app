package com.walle.credit.calc.controller;

import com.walle.credit.calc.dto.CreditDataDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CalcControllerTest {

    private static final String HOST = "http://localhost:";
    private static final String PATH = "/calculate";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void calculateWithIntegerNumbers() throws Exception {
        CreditDataDto dto = new CreditDataDto("700000", "2.24", "200000", 20);
        ResponseEntity<String> response = restTemplate.postForEntity(HOST + port + PATH, dto, String.class);
        System.out.println(response.getBody());
    }

    @Test
    public void calculateWithDecimalDotNumbers() throws Exception {
        CreditDataDto dto = new CreditDataDto("50.00", "2.95", "10.10", 20);
        ResponseEntity<String> response = restTemplate.postForEntity(HOST + port + PATH, dto, String.class);
        System.out.println(response.getBody());
    }
    
    @Test
    public void shouldFailToConvertToNumber() throws Exception {
        CreditDataDto dto = new CreditDataDto("50string", "2", "10", 20);
        ResponseEntity<String> response = restTemplate.postForEntity(HOST + port + PATH, dto, String.class);
        System.out.println(response.getBody());
    }
}
