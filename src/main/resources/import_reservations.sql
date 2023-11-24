BEGIN;

INSERT INTO reservations(id, flight_id, passenger_id)
VALUES (NEXTVAL('reservations_sequence_in_database'),
    (SELECT id FROM flights WHERE id = 1),
    (SELECT id FROM passengers WHERE id = 1));

INSERT INTO reservations(id, flight_id, passenger_id)
VALUES (NEXTVAL('reservations_sequence_in_database'),
    (SELECT id FROM flights WHERE id = 51),
    (SELECT id FROM passengers WHERE id = 51));

COMMIT;