package com.tak.soda.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

	@Column(nullable =false)
	private long hostId;

	@Column(nullable = false)
	private String inviteCode;

	@Column
	private LocalDateTime startTime;
	
	@Column
	private LocalDateTime endTime;

	@OneToMany(mappedBy = "meeting")
	private List<MeetingMember> meetings = new ArrayList<>();

	public void addMeeting(MeetingMember mm) {
		meetings.add(mm);
		mm.setMeeting(this);
	}
	
	public void updateEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}
}
