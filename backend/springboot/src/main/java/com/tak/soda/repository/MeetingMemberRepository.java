package com.tak.soda.repository;

import com.tak.soda.domain.Company;
import com.tak.soda.domain.MeetingMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MeetingMemberRepository {

    private final EntityManager em;

    public void save(MeetingMember meetingMember) {
        if(meetingMember.getId() == null)
            em.persist(meetingMember);
        else
            em.merge(meetingMember);
    }

    public List<MeetingMember> checkDuplicate(Long m_id, Long u_id) {
        return em.createQuery("SELECT m FROM MeetingMember m WHERE m.member.id=:u_id and m.meeting.id=:m_id", MeetingMember.class)
                .setParameter("m_id", m_id)
                .setParameter("u_id", u_id)
                .getResultList();
    }

    public MeetingMember findById(Long mm_id) {
        return em.createQuery("SELECT mm FROM MeetingMember mm WHERE mm.id=:mm_id", MeetingMember.class)
                .setParameter("mm_id", mm_id)
                .getSingleResult();
    }

    @Transactional
    public void deleteByMm_Id(Long mm_id) {
        String qlString = "DELETE FROM MeetingMember mm where mm.id=:mm_id";

        em.createQuery(qlString)
                .setParameter("mm_id", mm_id)
                .executeUpdate();
    }
    
    @Transactional
    public void deleteById(Long mu_id) {
        String qlString = "DELETE FROM MeetingMember mm where mm.id=:mu_id";

        em.createQuery(qlString)
                .setParameter("mu_id", mu_id)
                .executeUpdate();

//        em.createQuery("DELETE FROM Member m where m.id=:u_id")
//                .setParameter("u_id", u_id)
//                .executeUpdate();

    }
}
