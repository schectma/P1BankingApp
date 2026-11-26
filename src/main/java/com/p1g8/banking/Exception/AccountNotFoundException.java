package com.p1g8.banking.Exception;
//US 48 ---- Invalid and unauthorized transactions gets rejected
//US-7, 8, 14, 20, 47 --requested bank account does not exist in database

public class AccountNotFoundException extends BankingException{
    public AccountNotFoundException(String message) {
        super(message);
    }
}
