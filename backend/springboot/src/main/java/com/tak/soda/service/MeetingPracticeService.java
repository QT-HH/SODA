package com.tak.soda.service;

import com.tak.soda.domain.MeetingPractice;
import com.tak.soda.function.RandomAccessToken;
import com.tak.soda.repository.MeetingPracticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        String room_name;

        while (true) {
            room_name = randomAccessToken.makeToken(10);

            if (!findByName(room_name))
                break;
        }
        System.out.println(room_name);
        MeetingPractice meetingPractice = new MeetingPractice(room_name);
        meetingPracticeRepository.save(meetingPractice);

        return meetingPractice;
    }

    /**
     * 중복되는 방이 있는지 확인
     * @param name
     * @return
     */
    public boolean findByName(String name) {
        MeetingPractice meetingPractice = meetingPracticeRepository.findByName(name);

        if (meetingPractice != null) {
            return true;
        }

        return false;
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
