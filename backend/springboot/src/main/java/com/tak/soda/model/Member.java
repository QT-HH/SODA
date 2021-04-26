package com.tak.soda.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Member {
	@Id
	@Column(nullable = false, updatable = false)
	private long userId;
	
	@Column(nullable = true)
	private String userName;
	
	@Column(nullable = false)
	private long roomId;
	
	@Column(nullable = false, updatable = false)
	private int ishost;
}
