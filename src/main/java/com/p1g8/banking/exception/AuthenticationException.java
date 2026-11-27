package com.p1g8.banking.exception;
//US-2 -> Customer wants to log in securely.
//US-19 -> Employee - login with employee credentials

public class AuthenticationException extends BankingException{
    public AuthenticationException(String message) {
        super(message);
    }
}
