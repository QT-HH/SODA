package com.tak.soda.repository;

import com.tak.soda.domain.MeetingMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MeetingMemberRepository {

    private final EntityManager em;

    public List<MeetingMember> checkDuplicate(Long m_id, Long u_id) {
        return em.createQuery("SELECT m FROM MeetingMember m WHERE m.member.id=:u_id and m.meeting.id=:m_id", MeetingMember.class)
                .setParameter("m_id", m_id)
                .setParameter("u_id", u_id)
                .getResultList();
    }
}
