package com.tak.soda.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class MeetingMember {

    @Id
    @GeneratedValue
    @Column(name = "mu_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "u_id")
    private Member member;  // 미팅 참여자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "m_id")
    private Meeting meeting; // 미팅방

    @Enumerated
    @Column(name = "mm_status")
    private MemberStatus status;
}
