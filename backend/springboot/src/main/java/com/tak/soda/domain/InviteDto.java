package com.tak.soda.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.util.List;

@Embeddable
@Getter @Setter
public class InviteDto {

    private boolean isInterviewee;
    private List<String> emails;

}
