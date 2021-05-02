package com.tak.soda.domain;

import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Embeddable
@Getter @Setter @ToString
public class CompanyDto {
	private Long id;
	private String cname;
	private String authCode;
}
