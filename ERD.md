```mermaid
---
title: Banking Model/Entity Relationships
---
erDiagram
    CUSTOMER ||--o{ ACCOUNT : owns
    CUSTOMER{
        long customerId PK
        string firstName
        string lastName
        string username
        string password
        datetime createdAt
    }
    ACCOUNT{
        long accountId PK
        long customerId FK
        string accountNumber
        AccountType type
        numeric balance
        AccountStatus status
        datetime createdAt
    }
```