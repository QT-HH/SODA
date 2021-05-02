package com.tak.soda.domain;

import javax.persistence.Embeddable;

import lombok.*;

@Embeddable
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CompanyDto {

	private String cname;
	private String uname;
	private String cjob;
	private String cphone;
	private String cemail;
}
