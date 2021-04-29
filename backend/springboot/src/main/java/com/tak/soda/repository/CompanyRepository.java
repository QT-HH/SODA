package com.tak.soda.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tak.soda.model.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer>{
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE companys set cidentify=:token WHERE cid=:cid", nativeQuery=true)
	void modifyCidentify(@Param("cid") int cid, @Param("token")String token) throws Exception;
}
