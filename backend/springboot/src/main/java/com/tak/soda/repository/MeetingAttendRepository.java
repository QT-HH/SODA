package com.tak.soda.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MeetingAttendRepository {

    @Autowired EntityManager em;

    public List<Long> findByEmailAndInviteCode(String email, String inviteCode) {
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


        List<Long> ids = new ArrayList<>();

        Long u_id = -1L;
        Long mm_id = -1L;

        for(Object[] row: results) {
            System.out.println(row[0]);
            ids.add((Long) row[0]);
            ids.add((Long) row[1]);
        }

        return ids;
    }
}
