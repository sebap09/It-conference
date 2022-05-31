package com.example.webservice.Services;

import com.example.webservice.Entities.InterestViews.CategoryInterestView;
import com.example.webservice.Entities.Lecture;
import com.example.webservice.Entities.InterestViews.LectureInterestView;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InterestService {

    private final LectureService lectureService;

    public InterestService(@Lazy LectureService lectureService) {
        this.lectureService = lectureService;
    }

    public List<LectureInterestView> getLectureInterest(){
        List<Lecture> allLectures=lectureService.getLectures();
        List<LectureInterestView> lectureInterest = new ArrayList<>();
        for(Lecture lecture : allLectures){
            lectureInterest.add(new LectureInterestView(lecture));
        }

        return lectureInterest;
    }

    public List<CategoryInterestView> getLectureCategoryInterest() {
        List<LectureInterestView> lectureInterestList = getLectureInterest();
        List<CategoryInterestView> categoryInterestViewList = new ArrayList<>();
        categoryInterestViewList.add(new CategoryInterestView("1"));
        categoryInterestViewList.add(new CategoryInterestView("2"));
        categoryInterestViewList.add(new CategoryInterestView("3"));

        for (LectureInterestView lectureInterestView : lectureInterestList) {
            String lectureCategory = lectureInterestView.getLecture().getLectureCategory();
            switch (lectureCategory) {
                case "1" -> categoryInterestViewList.get(0).calculateCategoryInterest(lectureInterestView.getLectureInterest());
                case "2" -> categoryInterestViewList.get(1).calculateCategoryInterest(lectureInterestView.getLectureInterest());
                case "3" -> categoryInterestViewList.get(2).calculateCategoryInterest(lectureInterestView.getLectureInterest());
            }
        }
        return categoryInterestViewList;
    }
}
