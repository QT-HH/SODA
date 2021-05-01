package com.tak.soda.domain;

import java.util.Date;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter 
public class Meeting {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "room_id", nullable = false, updatable = false)
	private long roomId;
	
	@Column(nullable = true)
	private String roomName;
	
	@Column(nullable = false)
	private String inviteCode;
	
	@Column(nullable =false)
	private long hostId;
	
	@Column
	private Date startTime;
	
	@Column
	private Date endTime;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "u_id")
	private Member member;
	
	
	public void setMember(Member member) {
		this.member = member;
		member.getMeetings().add(this);
	}
	
	public void updateEndTime(Date endTime) {
		this.endTime = endTime;
	}
}
