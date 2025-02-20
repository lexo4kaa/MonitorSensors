--liquibase formatted sql

--changeset krupenko:1
SELECT @viewer_id := id from roles r WHERE r.name = 'Viewer';
SELECT @administrator_id := id from roles r WHERE r.name = 'Administrator';

INSERT INTO users
    (username, password, role_id)
VALUES ('user', '{noop}user', @viewer_id),
       ('admin', '{noop}admin', @administrator_id);