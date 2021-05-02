package com.tak.soda.domain;

import lombok.Data;

import javax.persistence.Embeddable;
import java.util.List;

@Embeddable
@Data
public class MemberDto {
    private Long id;
    private String uName;
    private String email;
    private String role;
    private String phone;
    private MemberStatus status;
    private String cName;
    private List<String> meetings;
}
