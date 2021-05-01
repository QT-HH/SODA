package com.tak.soda.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tak.soda.domain.Member;

@Repository
public class MemberRepository{

	@PersistenceContext
	private EntityManager em;
	
	public List<Member> findByEmail(String email) {
		return em.createQuery("SELECT m FROM member m where m.cemail=:cemail", Member.class)
				.setParameter("cemail", email)
				.getResultList();
	}
	
	public void save(Member member) {
		em.persist(member);
	}
	
	public Member findById(Long id){
		return em.find(Member.class, id);
	}
}
