package com.tak.soda.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tak.soda.model.Company;
import com.tak.soda.repository.CompanyRepository;

@Service
public class CompanyService {
	@Autowired
	private CompanyRepository companyRepository;
	
	public boolean newCompany(Company company) {
		companyRepository.save(company);
		return true;
	}
	
	public void modifyCompany(int cid, String token) throws Exception {
		companyRepository.modifyCidentify(cid, token);
	}
	
	public List<Company> showCompany() {
		return companyRepository.findAll();
	}
	
	public boolean deltCompany(Integer cid) {
		companyRepository.deleteById(cid);
		
		return true;
	}
	
}
