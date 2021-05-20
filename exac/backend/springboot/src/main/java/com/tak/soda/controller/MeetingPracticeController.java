package com.tak.soda.controller;

import com.tak.soda.domain.MeetingPractice;
import com.tak.soda.service.MeetingPracticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@Api(tags = {"모의면접 컨트롤러"})
@RequestMapping("practice")
public class MeetingPracticeController {

    @Autowired
    MeetingPracticeService meetingPracticeService;

    @ApiOperation(value = "모의면접 방 생성")
    @GetMapping(value = "new")
    public ResponseEntity newMeetingPractice() {
        System.out.println("enter practice/new");
        MeetingPractice meetingPractice = meetingPracticeService.createRoom();

        return new ResponseEntity(meetingPractice, HttpStatus.OK);
    }

    @ApiOperation(value = "모의면접 방 삭제")
    @DeleteMapping(value = "del")
    public ResponseEntity delMeetingPractice(Long mp_id) {
        meetingPracticeService.deleteRoom(mp_id);

        return new ResponseEntity("삭제 성공", HttpStatus.OK);
    }

}
