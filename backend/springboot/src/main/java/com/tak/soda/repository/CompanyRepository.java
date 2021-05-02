package com.tak.soda.repository;

import java.util.List;

import javax.persistence.EntityManager;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import com.tak.soda.domain.Company;

@Repository
@RequiredArgsConstructor
public class CompanyRepository{

	private final EntityManager em;
	
	public void save(Company company) {
		em.persist(company);
	}
	
	public Company findOne(Long id) {
		return em.find(Company.class, id);
	}
	
	public List<Company> findAll() {
		return em.createQuery("SELECT c FROM Company c", Company.class)
				.getResultList();
	}
	
	public List<Company> findByName(String cname) {
		return em.createQuery("SELECT c FROM Company c WHERE c.name=:cname", Company.class)
				.setParameter("cname", cname)
				.getResultList();
	}

	public List<Company> findByAuth(String authCode) {
		return em.createQuery("SELECT c FROM Company c WHERE c.authCode=:authCode", Company.class)
				.setParameter("authCode", authCode)
				.getResultList();
	}
	
	public void delete(Company company) {
		em.remove(company);
	}
	
//	@Transactional
//	@Query(value = "UPDATE companys set cidentify=:token WHERE cid=:cid", nativeQuery=true)
//	public void modifyCidentify(@Param("cid") int cid, @Param("token")String token) throws Exception;
}
