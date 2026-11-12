package com.p1g8.banking.Model;

import java.time.Instant;
import java.util.List;

public class NoteFilter{
       private  Long accountId;  //null = search all accounts
        private  Long employeeId; //null = any author
        private List<String> tags; //already normalized tags; null or empty = no tag filter
        private boolean matchAll; //true = note must have every tag ($all), false = any tag ($in)
        private Instant from;  //inclusive; null = no lower bound
        private Instant toExclusive; //exclusive; null = no upper bound

    public NoteFilter(Long accountId, Long employeeId, List<String> tags, boolean matchAll, Instant from, Instant toExclusive) {
        this.accountId = accountId;
        this.employeeId = employeeId;
        this.tags = tags;
        this.matchAll = matchAll;
        this.from = from;
        this.toExclusive = toExclusive;
    }

    public Long getAccountId() {
        return accountId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public List<String> getTags() {
        return tags;
    }

    public boolean isMatchAll() {
        return matchAll;
    }

    public Instant getFrom() {
        return from;
    }

    public Instant getToExclusive() {
        return toExclusive;
    }
}
