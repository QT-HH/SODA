package com.tak.soda.domain.dto;

import lombok.Data;

import javax.persistence.Embeddable;
import java.util.List;

@Embeddable
@Data
public class MeetingDto {
    private Long id;
    private String mName;
    private String inviteCode;
    private List<String> emails;
}
