package com.p1g8.banking.exception;
//US-24 --> As a bank employee, freeze account that seems to be compromised -review necessary
//US-26 --> As a customer, clear message on when the account is frozen - contact bank
//US-9, 25, 48 --> prevents operations on frozen or closed accounts

public class InvalidAccountStatusException extends BankingException{
    public InvalidAccountStatusException(String message) {
        super(message);
    }
}
