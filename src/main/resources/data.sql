-- INSERT TEST DATA
INSERT INTO client (client_id, name)
VALUES (1, 'Vitalik'),
       (2, 'Pavel'),
       (3, 'Maxim');

INSERT INTO client_account (client_account_id, client_id, balance)
VALUES (1, 1, 10.10),
       (2, 2, 20.20),
       (3, 3, 30.30);