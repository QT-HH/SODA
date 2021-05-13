package com.tak.soda.service;

import com.tak.soda.domain.*;
import com.tak.soda.domain.dto.MemberDto;
import com.tak.soda.function.RandomAccessToken;
import com.tak.soda.repository.CompanyRepository;
import com.tak.soda.repository.MeetingRepository;
import com.tak.soda.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CompanyService {

	private final RandomAccessToken randomAccessToken;

	private final CompanyRepository companyRepository;
	private final MemberRepository memberRepository;
	private final MeetingRepository meetingRepository;

	// Junit Test
	public String get() {
		return "Hello JUnit5";
	}

	/**
	 * 기업 등록
	 * @param dto
	 * @return company_id
	 */
	@Transactional
	public Long newCompany(MemberDto dto) {
		validateDuplicateCompany(dto.getCName());

		Company company = new Company();

		company.setName(dto.getCName());
		companyRepository.save(company);

		Member member = Member.createMember(company, dto);

		memberRepository.save(member);

		return company.getId();
	}
	
	private void validateDuplicateCompany(String cName) {
		List<Company> findCompany = companyRepository.findByName(cName);
		
		if(!findCompany.isEmpty()) {
			throw new IllegalStateException("이미 등록된 기업입니다.");
		}
	}

	public boolean checkAuthCode(Long u_id) {
		Member member = memberRepository.findById(u_id);
		if (member.getCompany() != null) {
			return true;
		}
		return false;
	}

	@Transactional
	public String[] generateCode(Long u_id, String email) {
		String authCode = randomAccessToken.makeToken(10);
		String inviteCode = randomAccessToken.makeToken(15);

		Member member = memberRepository.findById(u_id);

		Company company = member.getCompany();
		company.setAuthCode(authCode);

		String room_name = member.getCompany().getName() +"_"+ inviteCode.substring(0,3);

		Meeting meeting = new Meeting();
		meeting.setHostId(u_id);
		meeting.setRoomName(room_name);
		meeting.setStartTime(LocalDateTime.now());
		meeting.setInviteCode(inviteCode);

		meetingRepository.save(meeting);

		MeetingMember mm = new MeetingMember();
		mm.setMeeting(meeting);
		mm.setMember(member);
		mm.setStatus(MemberStatus.PROGRESS);

		meeting.addMember(mm);
		member.addMeeting(mm);

		return new String[]{authCode, inviteCode};
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

	public Company matchAuthCode(String authCode) {
		List<Company> res = companyRepository.findByAuth(authCode);
		if(res.isEmpty()) {
			return null;
		}
		return res.get(0);
	}
//
//	public Member findMember(Long c_id) {
//		List<Member> res =  memberRepository.findByCId(c_id);
//
//		if(!res.isEmpty()) {
//			return res.get(0);
//		}
//		return null;
//	}

	public String findHost(Long c_id) {
		List<Member> res = memberRepository.findByCId(c_id);
		Member host = null;
		String ans = "";

		for(Member member: res) {
			ans = meetingRepository.findInviteCodeByHostId(member.getId());
			if(ans != null) {
				host = member;
				break;
			}
		}

		String[] tmp = ans.split(",");


		String host_name = host.getName();
		String inviteCode = tmp[1];

		return host_name +","+ inviteCode;
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
