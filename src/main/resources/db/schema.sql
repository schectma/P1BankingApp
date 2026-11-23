-- customer, employee, accounts, and money movement using PostgreSQL

--Customers

CREATE TABLE IF NOT EXISTS customers (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(120) NOT NULL,
    last_Name VARCHAR(120) NOT NULL,
    user_name VARCHAR(120) UNIQUE,
    password_hash VARCHAR(120) NOT NULl,
    created_At timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP
);

--Employees

CREATE TABLE IF NOT EXISTS employees (
    id BIGSERIAL PRIMARY KEY,
    first_Name VARCHAR(120) NOT NULL,
    last_Name VARCHAR(120) NOT NULL,
    user_name VARCHAR(120) NOT NULL UNIQUE,
    password_hash VARCHAR(120) NOT NULl,
    created_At timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Bank Accounts

CREATE TABLE IF NOT EXISTS bank_accounts (
    id BIGSERIAL PRIMARY KEY,
    customer_id BIGINT NOT NULL REFERENCES customers(id),
    account_number VARCHAR(20) NOT NULL UNIQUE,
    account_type VARCHAR(20) NOT NULL,
    balance NUMERIC(19, 2) NOT NULL DEFAULT 0,
    status VARCHAR (20) NOT NULL DEFAULT 'ACTIVE',
    created_at timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status_changed_by_employee_id BIGINT REFERENCES employees(id),
    status_changed_at timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- One row per account affected by a movement. A transfer wires two rows
-- (TRANSFER_OUT on the source, TRANSFER_IN on the destination), so each
-- account's history contains exactly its own entries.

CREATE TABLE IF NOT EXISTS transactions (
    id BIGSERIAL PRIMARY KEY,
    account_id BIGINT NOT NULL REFERENCES bank_accounts(id),
    transaction_type VARCHAR(20) NOT NULL CHECK (transaction_type IN ('DEPOSIT', 'WITHDRAWAL', 'TRANSFER_OUT', 'TRANSFER_IN', 'WIRE_OUT', 'WIRE_IN')),
    amount NUMERIC(19, 2) NOT NULL CHECK (amount >0),
    resulting_balance NUMERIC(19,2) NOT NULL CHECK (resulting_balance >=0),
    counterparty_account_id BIGINT REFERENCES bank_accounts(id),
    description VARCHAR(500),
    initiated_by_employee_id BIGINT REFERENCES employees (id),
    created_at timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CHECK (counterparty_account_id IS NULL OR counterparty_account_id <> account_id), -- != is not equal

    --Wire transfers are employee-inititated; nothing else is;

    CHECK ((transaction_type IN ('WIRE_OUT', 'WIRE_IN')) = (initiated_by_employee_id IS NOT NULL))

);

CREATE INDEX IF NOT EXISTS idx_accounts_customer  ON bank_accounts (customer_id);
CREATE INDEX IF NOT EXISTS idx_customers_name ON customers (first_Name, last_Name);
CREATE INDEX IF NOT EXISTS idx_transactions_account ON transactions (account_id, created_at DESC, id DESC);