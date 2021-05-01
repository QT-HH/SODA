package com.tak.soda.service;

import com.tak.soda.repository.CompanyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CompanyServiceTest {

    @Autowired
    CompanyService companyService;

    @DisplayName("Test Spring @Autowired Integration")
    @Test
    void testGet() {
        assertEquals("Hello JUnit5", companyService.get());
    }
}