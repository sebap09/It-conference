package com.example.webservice.Services;

import com.example.webservice.Entities.Lecture;
import com.example.webservice.Entities.User;
import com.example.webservice.Repositories.LectureRepository;
import com.example.webservice.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    public Lecture getLecture(Long lectureId){
        Optional<Lecture> optionalLecture=lectureRepository.findById(lectureId);
        if(optionalLecture.isPresent())
            return lectureRepository.findById(lectureId).get();
        return null;
    }

    public Lecture addReservation(Long lectureId, User user){
        Lecture lecture=getLecture(lectureId);
        lecture.addUser(user);
        lectureRepository.save(lecture);
        userService.addNewUser(user);
        return lecture;
    }
}
