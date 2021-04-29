package com.tak.soda.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data	
@Entity
@NoArgsConstructor
@Table(name = "companys")
public class Company {
	@Id
	@GeneratedValue
	int cid=0;
	@Column(nullable=false, length = 50)
	String cname;
	@Column(nullable=false, length = 20)
	String uname;
	@Column(nullable=false, length = 30, unique = true)
	String cemail;
	@Column(nullable=false, length = 100)
	String cjob;
	@Column(nullable=false, length = 30)
	String cphone;
	@Column(nullable=false, length = 15)
	String cidentify="";
	
	@Builder
	public Company(String cname, String uname, String cemail, String cjob, String cphone) {
		
		this.cname = cname;
		this.uname = uname;
		this.cemail = cemail;
		this.cjob = cjob;
		this.cphone = cphone;
	}
	
}
