INSERT INTO users (id, username, first_name, last_name, password)
VALUES
    ('ffb53a9d-64c4-4bb1-8f6e-7b6ca9b91938', 'johndoe', 'John', 'Doe', '$2a$10$oYbOMpW7c4j6x3Qsm627UO/K0DuEfB4stKTr5vTBJpI0HungKRBK6'),
    ('49b81801-2544-43de-9b94-8cd7242b3335', 'janedoe', 'Jane', 'Doe', '$2a$10$jMZSKDIP6FnuQpS0wYB8C..nFxP7ylDYkEMh.HRMPj1IW.eGeoUWi');

INSERT INTO user_accounts (id, user_id, balance, currency, status)
VALUES
    ('d147b7ec-7c92-4b25-9447-8d132a72ba83', 'ffb53a9d-64c4-4bb1-8f6e-7b6ca9b91938', 1000.00, 'ZAR', 'ACTIVE'),
    ('c6037991-5706-4540-84ec-6e7c9236a15a', '49b81801-2544-43de-9b94-8cd7242b3335', 2000.00, 'ZAR', 'ACTIVE');

