package com.example.webservice.Exceptions.LectureExceptions;

public class LectureNotFoundException extends RuntimeException{
    public LectureNotFoundException(){
        super("This user is not enrolled for this lecture");
    }
}
