CREATE SCHEMA IF NOT EXISTS public;

CREATE TABLE math_functions (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    count INT NOT NULL,
    xFrom DOUBLE NOT NULL,
    xTo DOUBLE NOT NULL
);

CREATE TABLE points (
    id SERIAL PRIMARY KEY,
    function_id INT NOT NULL,
    x DOUBLE NOT NULL,
    y DOUBLE NOT NULL,
    FOREIGN KEY (function_id) REFERENCES math_functions(id) ON DELETE CASCADE
);

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);
