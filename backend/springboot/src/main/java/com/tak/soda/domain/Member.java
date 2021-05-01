package com.tak.soda.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @ToString
public class Member {
	
	@Id @GeneratedValue
	@Column(name = "u_id")
	private long id;
	
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
	
	//==연관관계 메서드==//
	public void setCompany(Company company) {
		this.company = company;
		company.getMembers().add(this);
	}
	
	public void addMeeting(Meeting meeting) {
		meetings.add(meeting);
		meeting.setMember(this);
	}
	
}
