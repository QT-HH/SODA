package com.tak.soda.domain;

import javax.persistence.Embeddable;

import lombok.*;

@Embeddable
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CompanyDto {
	private Long id;
	private String cname;
	private String authCode;
}
