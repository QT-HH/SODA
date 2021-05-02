package com.tak.soda.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tak.soda.domain.Company;
import com.tak.soda.repository.CompanyRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class CompanyService {

	private final CompanyRepository companyRepository;
	
	// Junit Test
	public String get() {
		return "Hello JUnit5";
	}

	/**
	 * 기업 등록
	 * @param company
	 * @return company_id
	 */
	@Transactional
	public Long newCompany(Company company) {
		validateDuplicateCompany(company);
		companyRepository.save(company);
		return company.getId();
	}
	
	private void validateDuplicateCompany(Company company) {
		List<Company> findCompany = companyRepository.findByName(company.getName());
		
		if(!findCompany.isEmpty()) {
			throw new IllegalStateException("이미 등록된 기업입니다.");
		}
	}

	/**
	 * 기업 전체 조회
	 * @return company list
	 */
	public List<Company> showCompany() {
		return companyRepository.findAll();
	}
	
	/**
	 * 특정 기업 조회
	 * @param name
	 * @return company list
	 */
	public List<Company> findByName(String name) {
		return companyRepository.findByName(name);
	}
	
	/**
	 * 기업 삭제
	 * @param id
	 * @return true/false
	 */
	@Transactional
	public boolean deleteCompany(Long id) {
		Company findCompany = companyRepository.findOne(id);
		if(findCompany != null) {
			companyRepository.delete(findCompany);
			return true;
		}
		return false;
	}
}
