package com.p1g8.banking.exception;
//US-1 -> New customer - register for an account
// handles duplicate usernames, duplicate account numbers, etc.

public class DuplicateDataException extends BankingException{

    public DuplicateDataException(String message) {
        super(message);
    }

    public DuplicateDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
