package com.tak.soda.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class MeetingPractice {

    @Id @GeneratedValue
    @Column(name = "mp_id")
    private Long id;

    @Column(name = "mp_name", nullable = false)
    private String name;

    @Column(name = "host_id", nullable = false)
    private Long hostId;

    public MeetingPractice(String name, Long hostId) {
        this.name = name;
        this.hostId = hostId;
    }
}
