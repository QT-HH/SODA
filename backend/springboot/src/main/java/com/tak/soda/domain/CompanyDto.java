package com.tak.soda.domain;

import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Embeddable
@Getter @Setter @ToString
public class CompanyDto {

	private String cname;
	private String uname;
	private String cjob;
	private String cphone;
	private String cemail;
}
