package com.tak.soda.service;

import com.tak.soda.domain.MeetingPractice;
import com.tak.soda.repository.MeetingPracticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MeetingPracticeService {

    private final MeetingPracticeRepository meetingPracticeRepository;

    /**
     * 중복되는 방이 있는지 확인
     * @param name
     * @return
     */
    public Long findByName(String name) {
        MeetingPractice meetingPractice = meetingPracticeRepository.findByName(name);

        return meetingPractice.getId();
    }

    /**
     * 모의면접 방 생성
     * @param name
     * @param hostId
     * @return
     */
    public Long saveRoom(String name, Long hostId) {
        MeetingPractice meetingPractice = new MeetingPractice(name, hostId);

        meetingPracticeRepository.save(meetingPractice);

        return meetingPractice.getId();
    }

    /**
     * 모의면접 방 삭제
     * @param mp_id
     */
    public void deleteRoom(Long mp_id) {
        meetingPracticeRepository.deleteById(mp_id.intValue());
    }
}
