package com.example.webservice.Services;

import com.example.webservice.Entities.Lecture;
import com.example.webservice.Entities.User;
import com.example.webservice.Exceptions.LectureExceptions.LectureIsFullException;
import com.example.webservice.Exceptions.LectureExceptions.LectureNotFoundExceptionId;
import com.example.webservice.Exceptions.UserExceptions.LoginIsTakenException;
import com.example.webservice.Exceptions.UserExceptions.UserNotFoundExceptionLogin;
import com.example.webservice.Exceptions.UserExceptions.UserParticipationViolation;
import com.example.webservice.Repositories.LectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LectureService {
    private final LectureRepository lectureRepository;
    private final UserService userService;

    @Autowired
    public LectureService(LectureRepository lectureRepository, UserService userService) {
        this.lectureRepository = lectureRepository;
        this.userService= userService;
    }

    //get conference schedule   ->  // GET http://localhost:8080/api/schedule
    public List<Lecture> getLectures(){
        return lectureRepository.findAll();
    }





    //get lecture by id if exists   ->  used by other methods
    public Lecture getLecture(Long lectureId) {
        return lectureRepository.findById(lectureId).orElseThrow(()->new LectureNotFoundExceptionId(lectureId));
    }

//  get all lectures of user with this login    ->  // GET http://localhost:8080/api/lectures?login=
    public List<Lecture> getLecturesByLogin(String login){
        User user=userService.getUserByLogin(login).orElseThrow(UserNotFoundExceptionLogin::new);
        return List.copyOf(user.getReservations());
    }

//  if user exists, add to his reservations, if not create new user and add to his reservations    ->   // POST http://localhost:8080/api/lectures?lectureId=
    public Lecture addReservation(Long lectureId, User user){
            Lecture lecture = getLecture(lectureId);
            if(lecture.getUsers().size()>4)
                throw new LectureIsFullException();//max 5 users
            else {

                Optional<User> optionalUser = userService.getUserByLoginAndEmail(user.getLogin(), user.getEmail());
                if (optionalUser.isPresent()) {// if exists user with this login and email
                    for (Lecture value : optionalUser.get().getReservations()) {//iterate over reservations and compare prelection number
                        if (value.getPrelectionNumber().equals(lecture.getPrelectionNumber()))
                            throw new UserParticipationViolation();
                    }


                            lecture.addUser(optionalUser.get());
                } else {
                    userService.getUserByLogin(user.getLogin()).ifPresent((s)->{throw new LoginIsTakenException();});//if login is taken
                    lecture.addUser(user);
                    userService.addNewUser(user);
                }

                return lectureRepository.save(lecture);
            }
    }
//if user exists and has this reservation, remove it, else throw exception  ->  // DELETE http://localhost:8080/api/lectures/?login=&lectureId=
    public void removeReservation(String login, Long lectureId){
        User user=userService.getUserByLogin(login).orElseThrow(UserNotFoundExceptionLogin::new);
        if(user.getReservations().remove(getLecture(lectureId)))
            lectureRepository.save(getLecture(lectureId));
        else throw new LectureNotFoundExceptionId(lectureId);
    }
}
