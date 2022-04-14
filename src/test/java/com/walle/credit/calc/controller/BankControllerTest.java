package com.walle.credit.calc.controller;

import com.walle.credit.calc.dto.BankDto;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BankControllerTest {

    private static final String HOST = "http://localhost:";
    private static final String PATH_BANKS = "/banks";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testSaveNewBank() {
        BankDto bankDtoReq = new BankDto(null, "Name1", "112233B", "City", Map.of("Offer1", 7.8));
        ResponseEntity<BankDto> response = restTemplate.exchange(HOST + port + PATH_BANKS,
                HttpMethod.POST, new HttpEntity<>(bankDtoReq), BankDto.class);

        assertEquals(204, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(bankDtoReq.getName(), response.getBody().getName());
    }

    @Test
    public void testGetAllBanks() {
        BankDto bankDtoReq = new BankDto(null, "Name11", "112233B", "City11", Map.of("Offer1", 7.8));
        restTemplate.exchange(HOST + port + PATH_BANKS, HttpMethod.POST, new HttpEntity<>(bankDtoReq), BankDto.class);
        restTemplate.exchange(HOST + port + PATH_BANKS, HttpMethod.POST, new HttpEntity<>(bankDtoReq), BankDto.class);

        ResponseEntity<List<BankDto>> response = restTemplate.exchange(HOST + port + PATH_BANKS,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<BankDto>>() {});

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

}
