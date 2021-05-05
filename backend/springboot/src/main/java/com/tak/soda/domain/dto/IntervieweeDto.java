package com.tak.soda.domain.dto;

import com.tak.soda.domain.MeetingStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Setter @Getter
public class IntervieweeDto {

    private Long u_id; // 멤버 아이디
    private Long m_id; // 미팅방 아이디
    private Long mm_id; // 미팅방 멤버 식별번호
    private Long host_id; // 개설자 아이디
    private String u_email;
    private String u_name;
    private String u_phone;
    private String u_role;
    private MeetingStatus status;
}
