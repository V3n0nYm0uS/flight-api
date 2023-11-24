BEGIN;

INSERT INTO passengers(id, surname, firstname, email_address)
VALUES (NEXTVAL('passengers_sequence_in_database'), 'Leo', 'Nardo', 'leo.nardo@test.com');

INSERT INTO passengers(id, surname, firstname, email_address)
VALUES (NEXTVAL('passengers_sequence_in_database'), 'Nat', 'tanael', 'nat.tanael@test.com');

COMMIT;

