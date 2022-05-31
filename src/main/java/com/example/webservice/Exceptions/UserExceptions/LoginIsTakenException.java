package com.example.webservice.Exceptions.UserExceptions;

public class LoginIsTakenException extends RuntimeException{
    public LoginIsTakenException(){
        super("Ten login jest juz zajety!");
    }
}
