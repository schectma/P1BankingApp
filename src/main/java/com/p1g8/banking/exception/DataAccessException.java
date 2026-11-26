package com.p1g8.banking.exception;
/*US-46 : As a developer - failure to save a note or log entry
/ never undo or corrupt a completed trans. - system of record stays correct
/ US-47 : Clear error messages - Handles database related exceptions from PostgreSQL and MongoDB */

public class DataAccessException extends BankingException{

    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
