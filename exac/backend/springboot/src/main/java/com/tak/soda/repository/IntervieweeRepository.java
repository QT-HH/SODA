package com.tak.soda.repository;

import com.tak.soda.domain.dto.IntervieweeDto;
import com.tak.soda.domain.MemberStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class IntervieweeRepository {

    private final EntityManager em;

    public List<IntervieweeDto> findMemberByInviteCode(String inviteCode) {
        String qlString = "select u.id, m.id, mm.id, m.hostId, u.name, u.email, u.phone, u.role, mm.status " +
                "from Member u right join MeetingMember mm " +
                "on u.id=mm.member.id " +
                "inner join Meeting m " +
                "on mm.meeting.id=m.id " +
                "where m.inviteCode=:inviteCode " +
                "and u.role=:role";

        List<Object[]> results = em.createQuery(qlString, Object[].class)
                .setParameter("inviteCode", inviteCode)
                .setParameter("role", "면접자")
                .getResultList();

        List<IntervieweeDto> intervieweeDtoList = new ArrayList<>();

        for(Object[] row: results) {
            IntervieweeDto dto = new IntervieweeDto();

            dto.setU_id((Long) row[0]);
            dto.setM_id((Long) row[1]);
            dto.setMm_id((Long) row[2]);
            dto.setHost_id((Long) row[3]);

            dto.setU_name((String) row[4]);
            dto.setU_email((String) row[5]);
            dto.setU_phone((String) row[6]);
            dto.setU_role((String) row[7]);
            dto.setStatus((MemberStatus) row[8]);

            intervieweeDtoList.add(dto);
        }

        return intervieweeDtoList;
    }

}
