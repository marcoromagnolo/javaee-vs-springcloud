-- Operators
INSERT INTO operator (email, first_name, last_name, username, password) VALUES ('admin@example.com', 'Super', 'Administrator', 'admin', md5('password'));

-- Players
INSERT INTO player (id, nickname, username, password) VALUES (1, 'System User', 'system', md5('password'));
INSERT INTO player (id, nickname, username, password) VALUES (2, 'Poker Ace', 'pokerace', md5('password'));

-- Accounts
INSERT INTO account (age, email, first_name, last_name, sex, player_id) VALUES (null, 'admin@example.com', 'System', null, null, 1);
INSERT INTO account (age, email, first_name, last_name, sex, player_id) VALUES ('33', 'pokerace@example.com', 'Marco', 'Romagnolo', 'M', 2);

-- Wallets
INSERT INTO wallet (balance, player_id) VALUES (20000, 1);
INSERT INTO wallet (balance, player_id) VALUES (20000, 2);
