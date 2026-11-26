package com.p1g8.banking.exception;

//US-47 Customer wants error messages when an operation fails
// Understand how to correct the problem.
public class BankingException extends RuntimeException{
    public BankingException(String message){
        super(message);
    }

    public BankingException(String message, Throwable cause){
        super(message, cause);

    }
}
