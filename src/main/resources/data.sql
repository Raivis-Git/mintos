INSERT INTO currency (id, iso) VALUES (1, 'EUR');
INSERT INTO currency (id, iso) VALUES (2, 'GBP');
INSERT INTO currency (id, iso) VALUES (3, 'USD');

INSERT INTO client (id, full_name, phone_number, email) VALUES (10, 'Name Surname', '+9119119', 'name@surname.test');
INSERT INTO client (id, full_name, phone_number, email) VALUES (20, 'First Person', '+112345', 'first@person.test');
INSERT INTO client (id, full_name, phone_number, email) VALUES (30, 'Second Human', '+22355535353', 'second@human.test');
INSERT INTO client (id, full_name, phone_number, email) VALUES (40, 'Third Fourth', '+12199', 'third@forth.test');

INSERT INTO account (id, currency_id, balance, client_id) VALUES (11, 1, 100, 10);
INSERT INTO account (id, currency_id, balance, client_id) VALUES (12, 2, 0, 10);
INSERT INTO account (id, currency_id, balance, client_id) VALUES (13, 3, 130, 10);

INSERT INTO account (id, currency_id, balance, client_id) VALUES (21, 1, 100, 20);
INSERT INTO account (id, currency_id, balance, client_id) VALUES (22, 1, 254, 20);
INSERT INTO account (id, currency_id, balance, client_id) VALUES (23, 2, 700, 20);

INSERT INTO account (id, currency_id, balance, client_id) VALUES (31, 1, 100, 30);


