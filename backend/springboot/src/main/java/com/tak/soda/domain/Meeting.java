package com.tak.soda.domain;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)@Getter @Setter
public class Meeting {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "room_id", nullable = false, updatable = false)
	private Long id;
	
	@Column(nullable = true)
	private String roomName;
	
	@Column(nullable = false)
	private String inviteCode;
	
	@Column(nullable =false)
	private long hostId;
	
	@Column
	private LocalDateTime startTime;
	
	@Column
	private LocalDateTime endTime;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "u_id")
	private Member member;
	
	
	public void setMember(Member member) {
		this.member = member;
		member.getMeetings().add(this);
	}
	
	public void updateEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}
}
