-- PostgreSQL

CREATE USER pokerbo WITH PASSWORD 'pokerbo' CREATEDB;

CREATE DATABASE pokerbo
  WITH OWNER = pokerbo
       TEMPLATE = template0
       ENCODING = 'UTF8'
CONNECTION LIMIT = -1;
