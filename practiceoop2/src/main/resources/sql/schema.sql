CREATE SCHEMA if not exists public;

CREATE TABLE math_functions (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    count INT NOT NULL,
    xFrom DOUBLE NOT NULL,
    xTo DOUBLE NOT NULL
);

CREATE TABLE points (
    id SERIAL PRIMARY KEY,
    function INT NOT NULL,
    x DOUBLE NOT NULL,
    y DOUBLE NOT NULL,
    FOREIGN KEY (function_id) REFERENCES math_functions(id) ON DELETE CASCADE
);