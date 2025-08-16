CREATE TABLE launch(
    id UUID PRIMARY KEY,
    description VARCHAR(255) NOT NULL,
    amount DECIMAL(15,2) NOT NULL,
    transaction_date DATE NOT NULL,
    type VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL,

    account_id UUID NOT NULL,
    client_id UUID NOT NULL,
    category_id UUID NOT NULL,

    FOREIGN KEY (account_id) REFERENCES account(id)


);