package com.tak.soda.repository;

import com.tak.soda.domain.IntervieweeDto;
import com.tak.soda.domain.MeetingMember;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class InterVieweeRepository{

    private final EntityManager em;

    String query = "select mu.u_id, mu.m_id, m.host_id, u.u_email, u.u_name, u.u_phone, u.u_role, u.status " +
            "from member as u right join meeting_member as mu " +
            "on u.u_id=mu.u_id " +
            "inner join meeting as m " +
            "on mu.m_id=m.room_id " +
            "where m.invite_code=:inviteCode";

    String qlString = "select mm.member.id, mm.meeting.id, m.hostId, u.email, u.name, u.phone, u.role, u.status " +
            "from Member u right join MeetingMember mm " +
            "on u.id=mm.member.id " +
            "inner join Meeting m " +
            "on mm.meeting.id=m.id " +
            "where m.inviteCode=:inviteCode";
    public List<IntervieweeDto> findMemberByInviteCode(String inviteCode) {
        return em.createQuery(qlString)
                .setParameter("inviteCode", inviteCode)
                .getResultList();
    }
}
