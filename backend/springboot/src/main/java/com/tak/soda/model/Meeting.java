package com.tak.soda.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Meeting {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, updatable = false)
	private long roomId;
	
	@Column(nullable = true)
	private String roomName;
	
	@Column(nullable = false)
	private String inviteCode;
	
	@Column(nullable =false)
	private long hostId;

	@Column(nullable = false,  columnDefinition = "TINYINT", length=1)
	private int isPublic;
	
	@Column
	private Date startTime;
	
	@Column
	private Date endTime;

	public void updateEndTime(Date endTime) {
		this.endTime = endTime;
	}
}
