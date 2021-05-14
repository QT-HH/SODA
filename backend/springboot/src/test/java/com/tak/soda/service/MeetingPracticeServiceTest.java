package com.tak.soda.service;

import com.tak.soda.domain.MeetingPractice;
import com.tak.soda.repository.MeetingPracticeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MeetingPracticeServiceTest {

    @Autowired MeetingPracticeService meetingPracticeService;
    @Autowired MeetingPracticeRepository meetingPracticeRepository;

    @DisplayName("모의면접방 생성")
    @Test
    void testCreateRoom() {
        //Given

        //When
        MeetingPractice room = meetingPracticeService.createRoom();

        //Then
        assertEquals(room, meetingPracticeRepository.findByName(room.getName()));
    }

    @DisplayName("모의면접방 삭제")
    @Test
    void testDeleteRoom() {
        //Given
        MeetingPractice room = meetingPracticeService.createRoom();
        Long mp_id = room.getId();

        System.out.println(mp_id);
        //When
        meetingPracticeService.deleteRoom(mp_id);

        //Then
        assertEquals(Optional.empty(), meetingPracticeRepository.findById(mp_id));
    }
}