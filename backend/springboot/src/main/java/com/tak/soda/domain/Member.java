package com.tak.soda.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
	private List<Meeting> meetings = new ArrayList<>();

	//==생성 메서드==//
	public static Member createMember(Company company, CompanyDto info){
		Member member = new Member();

		member.setName(info.getUname());
		member.setEmail(info.getCemail());
		member.setRole(info.getCjob());
		member.setPhone(info.getCphone());
		member.setStatus(MemberStatus.PLAN);

		member.setCompany(company);

		return member;
	}

	//==연관관계 메서드==//
	public void setCompany(Company company) {
		this.company = company;
		company.getMembers().add(this);
	}
	
	public void addMeeting(Meeting meeting) {
		meetings.add(meeting);
		meeting.setMember(this);
	}

	//==비즈니스 로직==//
	/** 멤버 삭제 */

	//==조회 로직==//
	/** 전체 멤버 조회 */
	
}
