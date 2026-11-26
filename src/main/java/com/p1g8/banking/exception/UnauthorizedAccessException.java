package com.p1g8.banking.exception;
//US-21 -> Customers are blocked from employee-only features
//US-39 -> Employee notes to be hidden from customers
//US-48 -> Reject unauthorized transactions


public class UnauthorizedAccessException extends BankingException{
    public UnauthorizedAccessException(String message) {
        super(message);
    }
}
