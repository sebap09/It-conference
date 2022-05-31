package com.example.webservice.Exceptions.LectureExceptions;

import java.text.MessageFormat;

public class LectureNotFoundExceptionId extends RuntimeException{

    public LectureNotFoundExceptionId(Long id){
        super(MessageFormat.format("Could not find lecture with id: {0}",id));
    }
}
