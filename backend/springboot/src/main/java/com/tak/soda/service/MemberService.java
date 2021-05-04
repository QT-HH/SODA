package com.tak.soda.service;

import com.tak.soda.domain.*;
import com.tak.soda.repository.CompanyRepository;
import com.tak.soda.repository.MeetingRepository;
import com.tak.soda.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
	public Long join(MemberDto dto) {
		//중복 이메일 체크
		validateEmail(dto.getEmail());

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

	@Transactional
	public void createInterviewee(String email, Company company) {
		Member member = new Member();

		// 면접관 생성
		member.setCompany(company);
		member.setStatus(MemberStatus.PLAN);
		member.setEmail(email);
		member.setRole("면접관");
		member.setName(company.getName() + " 면접관");

		memberRepository.save(member);

	}

	@Transactional
	public void createInterviewer(String email) {
		Member member = new Member();

		// 면접관 생성
		member.setStatus(MemberStatus.PLAN);
		member.setEmail(email);
		member.setRole("면접자");
		member.setName(email.split("@")[0] + " 님");

		System.out.println(member.getName());
		memberRepository.save(member);
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

			dto.setU_id(member.getId());
			if(member.getCompany() != null) {
				dto.setCName(member.getCompany().getName());
			}
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

			dto.setU_id(member.getId());
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
	 * @return member_id
	 */
	@Transactional
	public Long updateStatus(Long u_id, MemberStatus status) {
		Member member = memberRepository.findById(u_id);

		member.setStatus(status);
		return member.getId();
	}
	@Transactional
	public Long updateEmail(Long u_id, String email) {
		Member member = memberRepository.findById(u_id);

		member.setEmail(email);
		return member.getId();
	}
	@Transactional
	public Long updateName(Long u_id, String u_name) {
		Member member = memberRepository.findById(u_id);

		member.setName(u_name);
		return member.getId();
	}
	@Transactional
	public Long updateCompany(Long u_id, String c_name) {
		Member member = memberRepository.findById(u_id);
		//기업 연동
		List<Company> findCompany = companyRepository.findByName(c_name);
		Company company;

		if(!findCompany.isEmpty()) {
			company = findCompany.get(0);
		}else{
			company = new Company();
			company.setName(c_name);
			companyRepository.save(company);
		}

		member.setCompany(company);
		return member.getId();
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
