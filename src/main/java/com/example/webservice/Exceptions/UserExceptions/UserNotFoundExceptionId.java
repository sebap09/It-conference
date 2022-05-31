package com.example.webservice.Exceptions.UserExceptions;

import java.text.MessageFormat;

public class UserNotFoundExceptionId extends RuntimeException{
        public UserNotFoundExceptionId(Long id){
            super(MessageFormat.format("Could not find user with id: {0}",id));
        }
    }


