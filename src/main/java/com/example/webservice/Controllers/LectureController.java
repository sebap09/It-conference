package com.example.webservice.Controllers;

import com.example.webservice.Entities.Lecture;
import com.example.webservice.Entities.User;
import com.example.webservice.Services.LectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class LectureController {
    //zmienic na liczbe mnoga (user->users)
    //zmienic zagniezdzanie danych z users/7/posts na 'query stringa' users?user_id=7
    //zadbac o to zeby / byly zawsze na koncu (spring o to zadba sam ale zwrocic uwage)
    //kiedy not found to dac 404 itd
    //zmienic mapowanie odpowiedzi serwera


    private final LectureService lectureService;

    @Autowired
    public LectureController(LectureService lectureService) {
        this.lectureService = lectureService;
    }

    // GET http://localhost:8080/api/schedule
    @GetMapping(value = "/schedule")
    public ResponseEntity<List<Lecture>> getSchedule(){
        return new ResponseEntity<>(lectureService.getLectures(),HttpStatus.OK);
    }

//    // GET http://localhost:8080/api/lectures/organisation
//    @GetMapping(value = "/lectures/organisation")
//    public ResponseEntity<List<Lecture>> getLectureInterest(){
//        return new ResponseEntity<>(lectureService.getLectureInterest(),HttpStatus.OK);
//    }

    //TU DODAC CustomException
    // GET http://localhost:8080/api/lectures?login=
    @GetMapping(value = "/lectures")
    public ResponseEntity<List<Lecture>> getLectureReservation(@RequestParam final String login){
        try {
            return new ResponseEntity<>(lectureService.getLecturesByLogin(login), HttpStatus.OK);
        }catch (RuntimeException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }




    //TU DODAC idRezerwacji i CustomException i chyba zmienic na PUT
    // POST http://localhost:8080/api/lectures?lectureId=
    @PostMapping(value = "/lectures")
    public ResponseEntity<Lecture> addLectureReservation(@RequestParam final Long lectureId, @RequestBody final User user) throws URISyntaxException {
        try {
            Lecture lecture = lectureService.addReservation(lectureId, user);
            return ResponseEntity.created(new URI("/lectures/" + "idRezerwacji")).body(lecture);
        }catch (RuntimeException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // DELETE http://localhost:8080/api/lectures/?login=&lectureId=
    @DeleteMapping(value = "/lectures")
    public ResponseEntity<Void> deleteLectureReservation(@RequestParam final String login,@RequestParam final Long lectureId){
        try {
            lectureService.removeReservation(login,lectureId);
            return ResponseEntity.ok().build();
        }catch (RuntimeException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }


}
