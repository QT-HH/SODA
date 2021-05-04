package com.tak.soda.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.util.List;

@Embeddable
@Getter @Setter
public class InviteDto {

    private String role;
    private String cName;
    private String inviteCode;
    private List<String> emails;
    private List<String> names;

}
