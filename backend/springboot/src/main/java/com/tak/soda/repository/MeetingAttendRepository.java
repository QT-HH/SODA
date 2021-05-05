package com.tak.soda.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MeetingAttendRepository {

    private final EntityManager em;

    public Long findByEmailAndInviteCode(String email, String inviteCode) {
        String qlString = "select u.id, mm.id " +
                "from Member u right join MeetingMember mm " +
                "on u.id=mm.member.id " +
                "inner join Meeting m " +
                "on mm.meeting.id=m.id " +
                "where u.email=:email " +
                "and m.inviteCode=:inviteCode";

        List<Object[]> results = em.createQuery(qlString, Object[].class)
                .setParameter("email", email)
                .setParameter("inviteCode", inviteCode)
                .getResultList();

        Long u_id = -1L;

        for(Object[] row: results) {
            System.out.println(row[0]);
            u_id = (Long) row[0];
        }

        return u_id;
    }
}
