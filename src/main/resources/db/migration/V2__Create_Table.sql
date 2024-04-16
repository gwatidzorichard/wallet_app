CREATE TABLE transactions
(
    id                   UUID                        NOT NULL,
    sender_account_id    UUID                        NOT NULL,
    recipient_account_id UUID                        NOT NULL,
    amount               DECIMAL                     NOT NULL,
    transaction_date     TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    reference            VARCHAR(255)                NOT NULL,
    CONSTRAINT pk_transactions PRIMARY KEY (id)
);

CREATE TABLE user_accounts
(
    id       UUID         NOT NULL,
    user_id  UUID         NOT NULL,
    balance  DECIMAL      NOT NULL,
    currency VARCHAR(255) NOT NULL,
    status   VARCHAR(255) NOT NULL,
    CONSTRAINT pk_user_accounts PRIMARY KEY (id)
);

CREATE TABLE users
(
    id         UUID         NOT NULL,
    username   VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,
    password   VARCHAR(255) NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE user_accounts
    ADD CONSTRAINT uc_user_accounts_user UNIQUE (user_id);

ALTER TABLE users
    ADD CONSTRAINT uc_users_username UNIQUE (username);

ALTER TABLE transactions
    ADD CONSTRAINT FK_TRANSACTIONS_ON_RECIPIENT_ACCOUNT FOREIGN KEY (recipient_account_id) REFERENCES user_accounts (id);

ALTER TABLE transactions
    ADD CONSTRAINT FK_TRANSACTIONS_ON_SENDER_ACCOUNT FOREIGN KEY (sender_account_id) REFERENCES user_accounts (id);

ALTER TABLE user_accounts
    ADD CONSTRAINT FK_USER_ACCOUNTS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);
