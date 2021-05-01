package com.tak.soda.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.tak.soda.domain.Company;

@Repository
public class CompanyRepository{
	
	@PersistenceContext
	private EntityManager em;
	
	public void save(Company company) {
		em.persist(company);
	}
	
	public Company findOne(Long id) {
		return em.find(Company.class, id);
	}
	
	public List<Company> findAll() {
		return em.createQuery("SELECT * FROM companys", Company.class)
				.getResultList();
	}
	
	public List<Company> findByName(String cname) {
		return em.createQuery("SELECT * FROM companys WHERE c_name=:cname", Company.class)
				.setParameter("cname", cname)
				.getResultList();
	}
	
	public void delete(Company company) {
		em.remove(company);
	}
	
//	@Transactional
//	@Query(value = "UPDATE companys set cidentify=:token WHERE cid=:cid", nativeQuery=true)
//	public void modifyCidentify(@Param("cid") int cid, @Param("token")String token) throws Exception;
}
