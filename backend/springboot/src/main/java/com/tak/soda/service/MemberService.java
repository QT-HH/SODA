package com.tak.soda.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tak.soda.domain.Member;
import com.tak.soda.repository.MemberRepository;

@Service
@Transactional(readOnly = true)
public class MemberService {
	
	@Autowired
	MemberRepository memberRepository;
	
	/**
	 * 회원 등록
	 * @param member
	 */
	@Transactional
	public Long join(Member member) {
		validateEmail(member.getEmail());
		memberRepository.save(member);
		
		return member.getId();
	}
	
	/* 이메일 중복 확인 */
	public void validateEmail(String email) {
		List<Member> res = memberRepository.findByEmail(email);
		
		if(!res.isEmpty()) {
			throw new IllegalStateException("이미 존재하는 이메일입니다.");
		}
	}
	
	@Transactional
	public void updateToken(long id, String token) {
		
		Member findMember = memberRepository.findById(id);

	}
}
