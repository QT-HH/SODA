package com.tak.soda.service;

import com.tak.soda.domain.Company;
import com.tak.soda.function.RandomAccessToken;
import com.tak.soda.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CompanyService {

	private final RandomAccessToken randomAccessToken;
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
		String auth = randomAccessToken.makeToken(10);
		company.setAuthCode(auth);

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

	public boolean matchAuthCode(String authCode) {
		if(companyRepository.findByAuth(authCode).isEmpty()) {
			return false;
		}
		return true;
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
