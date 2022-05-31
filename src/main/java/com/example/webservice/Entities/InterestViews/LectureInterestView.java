package com.example.webservice.Entities.InterestViews;

import com.example.webservice.Entities.Lecture;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
public class LectureInterestView {
    private Lecture lecture;
    private Double lectureInterest;

    public LectureInterestView(Lecture lecture){
        this.lecture=lecture;
        this.lectureInterest=calculateLectureInterest();
    }

    private Double calculateLectureInterest(){
        return (double) this.lecture.getUsers().size()/5*100;
    }

}
