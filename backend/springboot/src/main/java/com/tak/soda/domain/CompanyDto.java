package com.tak.soda.domain;

import javax.persistence.Embeddable;

import lombok.*;

@Embeddable
@Getter @Setter
@ToString
public class CompanyDto {
	private Long id;
	private String cname;
	private String authCode;
}
