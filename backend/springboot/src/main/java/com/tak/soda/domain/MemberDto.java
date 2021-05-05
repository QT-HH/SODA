package com.tak.soda.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.util.List;

@Embeddable
@Data
@NoArgsConstructor
public class MemberDto {
    private Long u_id;
    private Long c_id;

    private String uName;
    private String email;
    private String role;
    private String phone;
    private MeetingStatus status;
    private String cName;
    private List<String> meetings;

    public MemberDto(String cName, String uName, String role, String phone, String email) {
        this.cName = cName;
        this.uName = uName;
        this.role = role;
        this.phone = phone;
        this.email = email;
    }
}
