--liquibase formatted sql

--changeset krupenko:1
CREATE TABLE types
(
    id    INT AUTO_INCREMENT PRIMARY KEY,
    value VARCHAR(50) NOT NULL
);

CREATE TABLE units
(
    id    INT AUTO_INCREMENT PRIMARY KEY,
    value VARCHAR(50) NOT NULL
);

CREATE TABLE sensors
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(30) NOT NULL,
    model       VARCHAR(15) NOT NULL,
    from_value  INT         NOT NULL,
    to_value    INT         NOT NULL,
    type_id     INT,
    unit_id     INT,
    location    VARCHAR(40),
    description VARCHAR(200),
    FOREIGN KEY (type_id) REFERENCES types (id),
    FOREIGN KEY (unit_id) REFERENCES units (id)
);

--changeset krupenko:2
CREATE TABLE roles
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

--changeset krupenko:3
CREATE TABLE users
(
    id       INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(30)  NOT NULL UNIQUE,
    password VARCHAR(128) NOT NULL,
    role_id  INT,
    FOREIGN KEY (role_id) REFERENCES roles (id)
);
