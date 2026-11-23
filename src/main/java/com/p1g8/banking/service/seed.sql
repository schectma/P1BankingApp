INSERT INTO customers (
    first_name,
    last_name,
    user_name,
    password_hash
)
VALUES
('Alice', 'Smith', 'alice', 'password'),
('Bob', 'Jones', 'bob', 'password'),
('Carol', 'Davis', 'carol', 'password');

INSERT INTO employees (
    first_name,
    last_name,
    user_name,
    password_hash
)
VALUES
('Emma', 'Brown', 'ebrown', 'password'),
('Mike', 'Wilson', 'mwilson', 'password');

INSERT INTO bank_accounts (
    customer_id,
    account_number,
    account_type,
    balance,
    status
)
VALUES
(1,'10000001','CHECKING',1200,'ACTIVE'),
(1,'10000002','SAVINGS',5000,'ACTIVE'),
(2,'10000003','CHECKING',300,'ACTIVE'),
(3,'10000004','CHECKING',900,'FROZEN'),
(3,'10000005','SAVINGS',0,'CLOSED');

INSERT INTO transactions (
    account_id,
    transaction_type,
    amount,
    resulting_balance
)
VALUES
(1,'DEPOSIT',1000,1000),
(1,'DEPOSIT',200,1200);

INSERT INTO transactions (
    account_id,
    transaction_type,
    amount,
    resulting_balance
)
VALUES
(3,'WITHDRAWAL',50,300);

INSERT INTO transactions (
    account_id,
    transaction_type,
    amount,
    resulting_balance,
    counterparty_account_id
)
VALUES
(1,'TRANSFER_OUT',250,950,3);

INSERT INTO transactions (
    account_id,
    transaction_type,
    amount,
    resulting_balance,
    counterparty_account_id,
    initiated_by_employee_id
)
VALUES
(2,'WIRE_OUT',500,4500,3,1);
