package com.p1g8.banking.exception;
//US-36 -> tags saved in a consistent format
//US-48 -> Invalid and unauthorized transactions to be rejected

public class ValidationException extends BankingException{
    public ValidationException(String message) {
        super(message);
    }
}
