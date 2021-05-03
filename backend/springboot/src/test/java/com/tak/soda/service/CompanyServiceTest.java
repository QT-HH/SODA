package com.tak.soda.service;

import com.tak.soda.domain.Company;
import com.tak.soda.repository.CompanyRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CompanyServiceTest {

//    @Autowired
//    CompanyService companyService;
//    @Autowired
//    CompanyRepository companyRepository;
//
//
//    @DisplayName("Test Spring @Autowired Integration")
//    @Test
//    void testGet() {
//        assertEquals("Hello JUnit5", companyService.get());
//    }
//
//    @DisplayName("기업 등록 테스트")
//    @Test
//    void testNewCompany() {
//        //Given
//        Company company = new Company();
//        company.setName("삼성전자");
//        //When
//        Long saveId = companyService.newCompany(company);
//
//        //Then
//        assertEquals(company, companyRepository.findOne(saveId));
//    }
//
//    @DisplayName("기업 중복 테스트")
//    @Test
//    void testValidateDuplicateCompany() {
//        //Given
//        Company company1 = new Company();
//        Company company2 = new Company();
//
//        company1.setName("삼성전자");
//        company2.setName("삼성전자");
//
//        //When
//        companyService.newCompany(company1);
//        IllegalStateException e = assertThrows(IllegalStateException.class, () -> {
//            companyService.newCompany(company2);    // 예외가 발생해야 함!
//        });
//        //Then
//        String msg = e.getMessage();
//        assertEquals("이미 등록된 기업입니다.", e.getMessage());
//    }
//
//    @DisplayName("기업 전체 조회 테스트")
//    @Test
//    void testShowCompany() {
//        //Given
//        List<Company> allCompany = new ArrayList<>();
//        Company company1 = new Company();
//        Company company2 = new Company();
//
//        company1.setName("삼성전자");
//        allCompany.add(company1);
//        company2.setName("네이버");
//        allCompany.add(company2);
//
//        //When
//        companyService.newCompany(company1);
//        companyService.newCompany(company2);
//        List<Company> res = companyService.showCompany();
//
//        //Then
//        assertEquals(allCompany, companyRepository.findAll());
//        assertEquals(res, companyRepository.findAll());
//    }
//
//    @DisplayName("기업 검색 테스트")
//    @Test
//    void testFindByName() {
//        //Given
//        Company company = new Company();
//        company.setName("삼성전자");
//
//        //When
//        companyService.newCompany(company);
//        List<Company> search = companyService.findByName("삼성전자");
//
//        //Then
//        assertEquals(search, companyRepository.findByName("삼성전자"));
//    }
//
//    @DisplayName("기업 삭제 테스트")
//    @Test
//    void testDeleteCompany() {
//        /**
//         * 일단 하나 저장하고
//         */
//        //Given
//        Company company = new Company();
//        company.setName("삼성전자");
//
//        //When
//        Long saveId = companyService.newCompany(company);
//
//        //Then
//        assertEquals(company, companyRepository.findOne(saveId));
//
//        /**
//         * 삭제해서 지워졌는지 확인
//         */
//        //Given
//        Long delId = saveId;
//
//        //When
//        boolean res = companyService.deleteCompany(delId);
//
//        //Then
//        assertEquals(true, res);
//        assertEquals(null, companyRepository.findOne(saveId));
//    }
}