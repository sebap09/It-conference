package com.example.webservice.Controllers;

import com.example.webservice.Entities.InterestViews.CategoryInterestView;
import com.example.webservice.Entities.Lecture;
import com.example.webservice.Entities.InterestViews.LectureInterestView;
import com.example.webservice.Entities.User;
import com.example.webservice.Exceptions.LectureExceptions.LectureIsFullException;
import com.example.webservice.Exceptions.LectureExceptions.LectureNotFoundException;
import com.example.webservice.Exceptions.LectureExceptions.LectureNotFoundExceptionId;
import com.example.webservice.Exceptions.UserExceptions.LoginIsTakenException;
import com.example.webservice.Exceptions.UserExceptions.UserNotFoundExceptionLogin;
import com.example.webservice.Exceptions.UserExceptions.UserParticipationViolation;
import com.example.webservice.Services.InterestService;
import com.example.webservice.Services.LectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LectureController {

    private final LectureService lectureService;
    private final InterestService interestService;

    @Autowired
    public LectureController(LectureService lectureService, InterestService interestService) {
        this.lectureService = lectureService;
        this.interestService = interestService;
    }

    // GET http://localhost:8080/api/schedule
    @GetMapping(value = "/schedule")
    public ResponseEntity<List<Lecture>> getSchedule(){
        return new ResponseEntity<>(lectureService.getLectures(),HttpStatus.OK);
    }

    // GET http://localhost:8080/api/lectures/interest/lecture
    @GetMapping(value = "/lectures/interest/lecture")
    public ResponseEntity<List<LectureInterestView>> getLectureInterest(){
        return new ResponseEntity<>(interestService.getLectureInterest(),HttpStatus.OK);
    }

    // GET http://localhost:8080/api/lectures/interest/category
    @GetMapping(value = "/lectures/interest/category")
    public ResponseEntity <List<CategoryInterestView>> getLectureCategoryInterest(){
        return new ResponseEntity<>(interestService.getLectureCategoryInterest(),HttpStatus.OK);
    }


    // GET http://localhost:8080/api/lectures?login=
    @GetMapping(value = "/lectures")
    public ResponseEntity<?> getLectureReservation(@RequestParam final String login){
        try {
            return new ResponseEntity<>(lectureService.getLecturesByLogin(login), HttpStatus.OK);
        }catch (UserNotFoundExceptionLogin ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }



    // POST http://localhost:8080/api/lectures?lectureId=
    @PostMapping(value = "/lectures")
    public ResponseEntity<?> addLectureReservation(@RequestParam final Long lectureId, @RequestBody final User user)  {
        try {
            Lecture lecture = lectureService.addReservation(lectureId, user);
            return new ResponseEntity<>(lecture,HttpStatus.CREATED);
        }catch (LectureIsFullException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
        catch (UserParticipationViolation ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
        catch (LoginIsTakenException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
        catch (LectureNotFoundExceptionId ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }


    }

    // DELETE http://localhost:8080/api/lectures/?login=&lectureId=
    @DeleteMapping(value = "/lectures")
    public ResponseEntity<?> deleteLectureReservation(@RequestParam final String login,@RequestParam final Long lectureId){
        try {
            lectureService.removeReservation(login,lectureId);
            return ResponseEntity.ok().build();
        }catch (UserNotFoundExceptionLogin ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
        catch (LectureNotFoundExceptionId ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
        catch (LectureNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }

    }


}
