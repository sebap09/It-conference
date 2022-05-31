package com.example.webservice.Services;

import com.example.webservice.Entities.Lecture;
import com.example.webservice.Entities.User;
import com.example.webservice.Repositories.LectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LectureService {
    private final LectureRepository lectureRepository;
    private final UserService userService;
//    private final InterestService interestService;

    @Autowired
    public LectureService(LectureRepository lectureRepository, UserService userService, InterestService interestService) {
        this.lectureRepository = lectureRepository;
        this.userService= userService;
//        this.interestService = interestService;
    }

    //get conference schedule   ->  // GET http://localhost:8080/api/schedule
    public List<Lecture> getLectures(){
        return lectureRepository.findAll();
    }





    //get lecture by id if exists   ->  used by other methods
    public Lecture getLecture(Long lectureId) throws RuntimeException{
//        Optional<Lecture> optionalLecture=lectureRepository.findById(lectureId);
//        if(optionalLecture.isPresent())
//            return lectureRepository.findById(lectureId).get();
//        else throw new RuntimeException();
//                                                              to jest to samo
        return lectureRepository.findById(lectureId).orElseThrow(RuntimeException::new);
    }

//  get all lectures of user with this login    ->  // GET http://localhost:8080/api/lectures?login=
    public List<Lecture> getLecturesByLogin(String login){
        User user=userService.getUserByLogin(login).orElseThrow(RuntimeException::new);
        return List.copyOf(user.getReservations());
    }

//  if user exists, add to his reservations, if not create new user and add to his reservations    ->   // POST http://localhost:8080/api/lectures?lectureId=
    public Lecture addReservation(Long lectureId, User user){
            Lecture lecture = getLecture(lectureId);
            if(lecture.getUsers().size()>4)
                throw new RuntimeException();//max 5 users
            else {

                Optional<User> optionalUser = userService.getUserByLoginAndEmail(user.getLogin(), user.getEmail());
                if (optionalUser.isPresent()) {// if exists user with this login and email
                    for (Lecture value : optionalUser.get().getReservations()) {//iterate over reservations and compare prelection number
                        if (value.getPrelectionNumber().equals(lecture.getPrelectionNumber()))
                            throw new RuntimeException();
                    }


                            lecture.addUser(optionalUser.get());
                } else {
                    userService.getUserByLogin(user.getLogin()).ifPresent((s)->{throw new RuntimeException("Podany login jest juz zajety");});//if login is taken
                    lecture.addUser(user);
                    userService.addNewUser(user);
                }

                return lectureRepository.save(lecture);
            }
    }
//if user exists and has this reservation, remove it, else throw exception  ->  // DELETE http://localhost:8080/api/lectures/?login=&lectureId=
    public void removeReservation(String login, Long lectureId){
        User user=userService.getUserByLogin(login).orElseThrow(RuntimeException::new);
        if(user.getReservations().remove(getLecture(lectureId)))
            lectureRepository.save(getLecture(lectureId));
        else throw new RuntimeException();
    }
}
