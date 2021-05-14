package com.tak.soda.service;

import com.tak.soda.domain.MeetingPractice;
import com.tak.soda.function.RandomAccessToken;
import com.tak.soda.repository.MeetingPracticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class MeetingPracticeService {

    private final RandomAccessToken randomAccessToken;
    private final MeetingPracticeRepository meetingPracticeRepository;

    /**
     * 모의면접 방 생성
     * @return mp_id
     */
    public MeetingPractice createRoom() {
        System.out.println("enter Service");
        LocalDateTime now = LocalDateTime.now();
        String room_name = randomAccessToken.makeToken(10);

        MeetingPractice meetingPractice = new MeetingPractice(room_name+now.toString());
        meetingPracticeRepository.save(meetingPractice);

        return meetingPractice;
    }

    /**
     * 모의면접 방 삭제
     * @param mp_id
     */
    @Transactional
    public void deleteRoom(Long mp_id) {
        meetingPracticeRepository.deleteById(mp_id);
    }
}
