package com.tak.soda.domain.dto;

import com.tak.soda.domain.MemberStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Setter @Getter
public class MeetingAttendDto {

    private Long u_id;
    private String u_name;
    //private String u_email;
    //private String u_phone;
    private String u_role;

    //private Long c_id;

    private Long mu_id;
    private MemberStatus mm_status;

    private Long room_id;
    //private LocalDateTime start_time;
    //private LocalDateTime end_time;
    private Long host_id;
    //private String inviteCode;
    private String room_name;

    //private String flag;

}
