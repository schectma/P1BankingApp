package com.p1g8.banking.exception;
// US-11 -> prevent overdrafts
// US-12,13,14,22,48 -> prevent overdrafts

public class InsufficientFundsException extends BankingException{
    public InsufficientFundsException(String message) {
        super(message);
    }
}
