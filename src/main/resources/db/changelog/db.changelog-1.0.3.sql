--liquibase formatted sql

--changeset krupenko:1
INSERT INTO sensors
(name, model, from_value, to_value, type_id, unit_id, location, description)
VALUES ('Barometer', 'ac-23', 22, 45, 1, 1, 'kitchen', 'description'),
       ('Hygrometer', 'bd-45', 0, 100, 4, 4, 'bedroom', 'H-description');

