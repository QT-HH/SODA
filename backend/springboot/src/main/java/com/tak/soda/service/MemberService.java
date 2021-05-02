package com.tak.soda.service;

import java.util.ArrayList;
import java.util.List;

import com.tak.soda.domain.*;
import com.tak.soda.repository.CompanyRepository;
import com.tak.soda.repository.MeetingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tak.soda.repository.MemberRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

	private final CompanyRepository companyRepository;
	private final MemberRepository memberRepository;
	private final MeetingRepository meetingRepository;
	
	/**
	 * 신규 멤버 등록(기업 담당자)
	 * @param dto
	 * @return member id
	 */
	@Transactional
	public Long join(CompanyDto dto) {
		//중복 이메일 체크
		validateEmail(dto.getCemail());

		//기업 연동
		List<Company> findCompany = companyRepository.findByName(dto.getCname());
		Company company;

		if(!findCompany.isEmpty()) {
			company = findCompany.get(0);
		}else{
			company = new Company();
			company.setName(dto.getCname());
			companyRepository.save(company);
		}

		Member member = Member.createMember(company, dto);

		memberRepository.save(member);
		
		return member.getId();
	}
	
	/* 이메일 중복 확인 */
	private void validateEmail(String email) {
		List<Member> res = memberRepository.findByEmail(email);
		
		if(!res.isEmpty()) {
			throw new IllegalStateException("이미 존재하는 이메일입니다.");
		}
	}

	public Member findById(Long id) {
		return memberRepository.findById(id);
	}

	/**
	 * 전체 조회
	 * @return
	 */
	public List<MemberDto> showAll() {
		List<Member> res = memberRepository.findAll();
		List<MemberDto> res_list = new ArrayList<>();

		for(Member member: res) {
			MemberDto dto = new MemberDto();

			dto.setId(member.getId());
			dto.setCName(member.getCompany().getName());
			dto.setUName(member.getName());
			dto.setPhone(member.getPhone());
			dto.setRole(member.getRole());
			dto.setEmail(member.getEmail());
			dto.setStatus(member.getStatus());

			res_list.add(dto);
		}
		return res_list;
	}

	/**
	 * 멤버 검색(이름)
	 * @param name
	 */
	public List<MemberDto> findByName(String name) {
		List<Member> res = memberRepository.findByName(name);
		List<MemberDto> res_list = new ArrayList<>();

		for(Member member: res) {
			MemberDto dto = new MemberDto();

			dto.setId(member.getId());
			dto.setCName(member.getCompany().getName());
			dto.setUName(member.getName());
			dto.setPhone(member.getPhone());
			dto.setRole(member.getRole());
			dto.setEmail(member.getEmail());
			dto.setStatus(member.getStatus());

			res_list.add(dto);
		}
		return res_list;
	}

	/**
	 * 멤버 업데이트
	 * @param dto
	 * @return member_id
	 */
	@Transactional
	public Long updateStatus(MemberDto dto) {
		Member member = memberRepository.findById(dto.getId());

		member.setStatus(dto.getStatus());

		System.out.println(member.toString());
		System.out.println(member.getStatus());
		return member.getId();
	}
	@Transactional
	public Long updateEmail(MemberDto dto) {
		Member member = memberRepository.findById(dto.getId());

		member.setEmail(dto.getEmail());
		return member.getId();
	}
	@Transactional
	public Long updateCompany(MemberDto dto) {
		Member member = memberRepository.findById(dto.getId());
		//기업 연동
		List<Company> findCompany = companyRepository.findByName(dto.getCName());
		Company company;

		if(!findCompany.isEmpty()) {
			company = findCompany.get(0);
		}else{
			company = new Company();
			company.setName(dto.getCName());
			companyRepository.save(company);
		}

		member.setCompany(company);
		return member.getId();
	}

	/**
	 * 멤버 상태 변경(예정, 진행, 종료)
	 * @param memberId
	 * @param status
	 */
	@Transactional
	public void updateStatus(Long memberId, MemberStatus status) {
		Member member = memberRepository.findById(memberId);

		member.setStatus(status);
		memberRepository.save(member);
	}


	@Transactional
	public boolean deleteMember(Long id) {
		Member member = memberRepository.findById(id);

		if(member != null) {
			memberRepository.delete(member);
			return true;
		}
		return false;
	}
}
