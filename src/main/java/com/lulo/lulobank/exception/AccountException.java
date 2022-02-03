package com.lulo.lulobank.exception;

public class AccountException extends Exception{

    public AccountException(String errorMessage){
        super(errorMessage);
    }
}
