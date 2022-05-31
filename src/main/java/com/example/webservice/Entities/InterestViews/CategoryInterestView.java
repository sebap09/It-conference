package com.example.webservice.Entities.InterestViews;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class CategoryInterestView {

        private String lectureCategory;
        private Double lectureCategoryInterest=0d;

        public CategoryInterestView(String lectureCategory){
            this.lectureCategory=lectureCategory;
        }

        public void calculateCategoryInterest(Double lectureInterest){
            this.lectureCategoryInterest+=(lectureInterest/3);
        }



}
