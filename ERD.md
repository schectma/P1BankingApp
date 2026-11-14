```mermaid
---
title: Banking Model/Entity Relationships
---
erDiagram
    CUSTOMER ||--o{ ACCOUNT : owns

    CUSTOMER{
        BIGINT id PK
        VARCHAR first_name
        VARCHAR last_name
        VARCHAR user_name UK
        VARCHAR password_hash 
        TIMESTAMPTZ created_at
    }
    ACCOUNT{
        BIGINT id PK
        BIGINT customer_id FK
        VARCHAR account_number
        AccountType type "CHECKING | SAVINGS"
        NUMERIC balance
        AccountStatus status "ACTIVE | FROZEN | CLOSED"
        TIMESTAMPTZ created_at
    }
    EMPLOYEE{
        BIGINT id PK
        VARCHAR first_name
        VARCHAR last_name
        VARCHAR user_name UK
        VARCHAR password_hash
        TIMESTAMPTZ created_at
    }
    TRANSACTION{
        BIGINT id PK
        BIGINT account_id FK "references account.id"
        BIGINT counterparty_account_id FK "references account.id"
        TransactionType type "DEPOSIT | WITHDRAWAL | TRANSFER_OUT | TRANSFER_IN | WIRE_OUT | WIRE_IN"
        NUMERIC amount
        BIGINT initiated_by_employee_id FK "references employee.id"
        TIMESTAMPTZ created_at
    }
    NOTE{
        int noteId PK,UK
        long accountId FK
        long employeeId FK
        long transactionId
        text content
        text tags[]
        date createdAt
    }
```