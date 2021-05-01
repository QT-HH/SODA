package com.tak.soda.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter 
@Table(name = "company")
public class Company {
	
	@Id	@GeneratedValue
	@Column(name = "c_id")
	private Long id;
	
	@Column(name="c_name")
	private String name;
	
	@OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
	List<Member> members = new ArrayList<>();
	
	
	//==연관관계 메서드==//
	public void addMember(Member member) {
		members.add(member);
		member.setCompany(this);
	}
}
