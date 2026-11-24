package com.p1g8.banking.dao;

import com.p1g8.banking.model.AccountNote;
import com.p1g8.banking.model.NoteFilter;

import java.util.List;

public interface AccountNoteDao {
    AccountNote create(AccountNote note);
    List<AccountNote> findByAccountId(long accountId);
    List<AccountNote> findByFilter(NoteFilter filter);
    AccountNote update(AccountNote note);
}
