package com.tak.soda.repository;

import com.tak.soda.domain.dto.IntervieweeDto;
import com.tak.soda.domain.dto.MeetingAttendDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JoinSQLRepository {

    private final EntityManager em;

    public List<IntervieweeDto> findMemberByInviteCode(String inviteCode) {
        String qlString = "select u.id, m.id, m.hostId, u.email, u.name, u.phone, u.role, u.status " +
                "from Member u right join MeetingMember mm " +
                "on u.id=mm.member.id " +
                "inner join Meeting m " +
                "on mm.meeting.id=m.id " +
                "where m.inviteCode=:inviteCode";

        return em.createQuery(qlString)
                .setParameter("inviteCode", inviteCode)
                .getResultList();
    }

    public List<MeetingAttendDto> findByEmailAndInviteCode(String email, String inviteCode) {
        String qlString = "select u.id, u.name, u.role, mm.id, mm.status, m.id, m.hostId, m.roomName " +
                "from Member u right join MeetingMember mm " +
                "on u.id=mm.member.id " +
                "inner join Meeting m " +
                "on mm.meeting.id=m.id " +
                "where u.email=:email " +
                "and m.inviteCode=:inviteCode";

        return em.createQuery(qlString)
                .setParameter("email", email)
                .setParameter("inviteCode", inviteCode)
                .getResultList();
    }
}
