package com.tak.soda.repository;

import java.util.List;

import javax.persistence.EntityManager;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import com.tak.soda.domain.Member;

@Repository
@RequiredArgsConstructor
public class MemberRepository{

	private final EntityManager em;
	
	public void save(Member member) {
		if(member.getId() == null)
			em.persist(member);
		else
			em.merge(member);
	}

	public List<Member> findAll() {
		return em.createQuery("SELECT m FROM Member m", Member.class)
				.getResultList();
	}

	public Member findById(Long id){
		return em.find(Member.class, id);
	}

	public List<Member> findByName(String name) {
		return em.createQuery("SELECT m FROM Member m WHERE m.name=:uname", Member.class)
				.setParameter("uname", name)
				.getResultList();
	}

	public Member findByEmailAndInviteCode(String email, String inviteCode) {
		String qlString = "";

		return em.createQuery("SELECT m FROM Member m WHERE m.email=:email and ", Member.class)
				.setParameter("email", email)
				.setParameter("inviteCode", inviteCode)
				.getSingleResult();
	}

	public List<Member> findByCId(Long c_id) {
		return em.createQuery("SELECT m FROM Member m WHERE m.company.id=:c_id", Member.class)
				.setParameter("c_id", c_id)
				.getResultList();
	}

	public List<Member> findByEmail(String email) {
		return em.createQuery("SELECT m FROM Member m where m.email=:email", Member.class)
				.setParameter("email", email)
				.getResultList();
	}

	public void delete(Member member) { em.remove(member); }
}
