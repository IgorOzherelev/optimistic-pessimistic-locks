CREATE TABLE IF NOT EXISTS accounts (
    id BIGINT PRIMARY KEY,
    sum INTEGER,
    opt_lock INTEGER
);

CREATE SEQUENCE IF NOT EXISTS accounts_id_sequence START WITH 1 MINVALUE 1 INCREMENT BY 1;

INSERT INTO accounts (id, sum, opt_lock) VALUES (1, 100, 1);
INSERT INTO accounts (id, sum, opt_lock) VALUES (2, 200, 1);