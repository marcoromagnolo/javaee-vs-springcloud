-- Operators
INSERT INTO operator (email, first_name, last_name, username, password) VALUES ('admin@example.com', 'Super', 'Administrator', 'admin', md5('password'));

-- Players
INSERT INTO player (nickname, username, password) VALUES ('System User', 'system', md5('password'));
INSERT INTO player (nickname, username, password) VALUES ('Poker Ace', 'pokerace', md5('password'));

-- Accounts
INSERT INTO account (age, email, first_name, last_name, sex, player_id) VALUES (null, 'admin@example.com', 'System', null, null, 1);

-- Wallets
INSERT INTO wallet (balance, player_id) VALUES (20000, 1);
