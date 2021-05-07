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

    public MeetingPractice(String name) {
        this.name = name;
    }
}
