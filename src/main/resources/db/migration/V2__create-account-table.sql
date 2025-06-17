CREATE TABLE account (
    id UUID PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    balance DECIMAL(15, 2) NOT NULL DEFAULT 0,
    client_id UUID NOT NULL,
    FOREIGN KEY (client_id) REFERENCES client(id)

);