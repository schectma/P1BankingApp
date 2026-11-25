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