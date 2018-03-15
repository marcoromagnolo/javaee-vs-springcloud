-- PostgreSQL

CREATE USER pokerbo WITH PASSWORD 'pokerbo' CREATEDB;

CREATE DATABASE pokerbo
  WITH OWNER = pokerbo
       TEMPLATE = template0
       ENCODING = 'UTF8'
CONNECTION LIMIT = -1;

INSERT INTO operator (email, first_name, last_name, username, password) VALUES ('admin@example.com', 'Super', 'Administrator', 'admin', md5('password'));

INSERT INTO player (nickname, username, password) VALUES ('Poker Ace', 'pokerace', md5('password'));