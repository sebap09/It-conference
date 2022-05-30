package com.example.webservice.Controllers;

import com.example.webservice.Entities.Lecture;
import com.example.webservice.Entities.User;
import com.example.webservice.Services.LectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class LectureController {
    private final LectureService lectureService;

    @Autowired
    public LectureController(LectureService lectureService) {
        this.lectureService = lectureService;
    }

    // POST http://localhost:8080/api/lecture/
    @PostMapping(value = "/lecture/{lectureId}")
    public ResponseEntity<Lecture> addLectureReservation(@PathVariable final Long lectureId, @RequestBody final User user) {
        Lecture lecture=lectureService.addReservation(lectureId,user);
        return new ResponseEntity<>(lecture, HttpStatus.OK);
    }
}
