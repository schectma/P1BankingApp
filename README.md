Project instructions: https://github.com/TRNG-2499/projects/blob/main/project_1/P1_BankingApp.md#project-instructions

# MongoDB Design
## Config
Atlas cluster with single database and two collections: `account_notes` and `activity_logs`.
## `account_notes`
```
{
  "_id": ObjectId,

  "accountId": 1001,

  "employeeId": 42,

  "category": "WIRE_TRANSFER",

  "text": "Customer requested same-day transfer.",

  "createdAt": ISODate("2026-11-23T15:20:00Z"),

  "transactionId": 812,

  "tags": [
    "verified",
    "high-value"
  ]
}
```

## `activity_logs`
```
{
  "_id": ObjectId("..."),

  "employeeId": 42,

  "actionType": "FREEZE_ACCOUNT",

  "accountId": 1001,

  "customerId": 300,

  "transactionId": 812,

  "timestamp": ISODate("2026-11-23T15:22:14Z"),

  "details": "Account frozen pending fraud investigation."
}
```

# MongoDB Setup
# Row Mapping Convention

Each DAO implementation owns its own mapper. E.g.
```java
private Customer mapRow(ResultSet rs) throws SQLException {
  return new Customer(
    rs.getLong("customer_id"),
    rs.getString("username"),
    rs.getString("password_hash")
  );
}
```
# Data Access Conventions
## Exceptions
MongoDB driver exceptions are caught and wrapped in
DataAccessException.
## Mapping
Java Model ↔ Mongo Document

toDocument()
    converts model objects into BSON Documents

fromDocument()
    converts BSON Documents into model objects

Instant is stored as java.util.Date
when persisted to MongoDB.

## Object ID
{
    "id": 123,
    ...
}
*Not* `{"_id": ObjectId(...)}`

## Indexes
account_notes
- accountId
- accountId + createdAt
![alt text](<Screenshot 2026-11-27 164413.png>)
activity_logs
- employeeId
- createdAt
![alt text](image.png)