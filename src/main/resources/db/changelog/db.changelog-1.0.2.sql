--liquibase formatted sql

--changeset krupenko:1
INSERT INTO types
    (value)
VALUES ('Pressure'),
       ('Voltage'),
       ('Temperature'),
       ('Humidity');

--changeset krupenko:2
INSERT INTO units
    (value)
VALUES ('bar'),
       ('voltage'),
       ('°С'),
       ('%');

--changeset krupenko:3
INSERT INTO roles
    (name)
VALUES ('Administrator'),
       ('Viewer');

