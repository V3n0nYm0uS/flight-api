BEGIN;

INSERT INTO flights(id, number, origin, destination, departure_date, departure_time, arrival_date, arrival_time, plane_id)
VALUES (NEXTVAL('flights_sequence_in_database'),
    'V72119', 'CPH', 'NTE', DATE '2023-11-24', TIME '3:25:00', DATE '2023-11-24', TIME '5:50:00',
    (SELECT id FROM planes WHERE id = 151)
);

INSERT INTO flights(id, number, origin, destination, departure_date, departure_time, arrival_date, arrival_time, plane_id)
VALUES (NEXTVAL('flights_sequence_in_database'),
    'UX1094', 'MAD', 'AMS', DATE '2023-11-25', TIME '15:25:00', DATE '2023-11-25', TIME '17:35:00',
    (SELECT id FROM planes WHERE id = 101)
);

COMMIT;