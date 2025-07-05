ALTER TABLE category
ADD COLUMN client_id UUID NOT NULL;

ALTER TABLE category
ADD CONSTRAINT fk_category_client FOREIGN KEY (client_id) REFERENCES client(id);