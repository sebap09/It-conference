package com.example.webservice.Exceptions.UserExceptions;

import java.text.MessageFormat;

public class UserNotFoundExceptionLogin extends RuntimeException {

    public UserNotFoundExceptionLogin(){
        super("Could not find user with this login");
    }
}
