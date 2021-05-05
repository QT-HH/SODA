package com.tak.soda.domain.dto;

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
