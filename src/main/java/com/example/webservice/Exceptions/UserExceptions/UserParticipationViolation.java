package com.example.webservice.Exceptions.UserExceptions;

public class UserParticipationViolation extends RuntimeException{
    public UserParticipationViolation(){
        super("User cannot participate in simultaneous lectures.");
    }
}
