package com.tak.soda.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
@Getter @Setter
public class Member {
	
	@Id @GeneratedValue
	@Column(name = "u_id")
	private Long id;
	
	@Column(name = "u_name")
	private String name;
	@Column(name = "u_email", nullable = false)
	private String email;
	@Column(name = "u_role")
	private String role;
	@Column(name = "u_phone")
	private String phone;

	@Enumerated(EnumType.STRING)
	private MemberStatus status; // 방에 들어올 수 있는지 여부(예정/진행/완료) - (PLAN/PROCESS/DONE)
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "c_id")
	private Company company;
	
	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
	private List<MeetingMember> meetings = new ArrayList<>();

	//==생성 메서드==//
	public static Member createMember(Company company, MemberDto info){
		Member member = new Member();

		member.setName(info.getUName());
		member.setEmail(info.getEmail());
		member.setRole(info.getRole());
		member.setPhone(info.getPhone());
		member.setStatus(MemberStatus.PROGRESS);

		member.setCompany(company);

		return member;
	}

	//==연관관계 메서드==//
	public void setCompany(Company company) {
		this.company = company;
		company.getMembers().add(this);
	}
	
	public void addMeeting(MeetingMember meeting) {
		meetings.add(meeting);
		meeting.setMember(this);
	}

	//==비즈니스 로직==//
	/** 미팅 코드 생성 */
	public void createMeeting() {

	}


	//==조회 로직==//
	/** 전체 멤버 조회 */
	
}
