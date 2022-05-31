package com.example.webservice.Exceptions.LectureExceptions;

public class LectureIsFullException extends RuntimeException{
    public LectureIsFullException(){
        super("This lecture is full.");
    }
}
