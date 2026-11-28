SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

CREATE SCHEMA IF NOT EXISTS zenon;

SET search_path TO zenon;


CREATE TABLE transactions (
          id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
          step INTEGER NOT NULL,
          type VARCHAR(20) NOT NULL,
          amount DECIMAL(20,2) NOT NULL,
          name_origin VARCHAR(50) NOT NULL,
          old_balance_origin DECIMAL(20,2) NOT NULL,
          new_balance_origin DECIMAL(20,2) NOT NULL,
          name_recipient VARCHAR(50) NOT NULL,
          old_balance_recipient DECIMAL(20,2) NOT NULL,
          new_balance_recipient DECIMAL(20,2) NOT NULL,
          is_fraud BOOLEAN DEFAULT FALSE,
          is_flagged_fraud BOOLEAN DEFAULT FALSE
);