-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database

BEGIN;


INSERT INTO planes(id, operator, model, immatriculation, capacity)
VALUES(NEXTVAL('planes_sequence_in_database'), 'AirbusIndustrie', 'AIRBUS A380',
 'F-ABCD', 10);
INSERT INTO planes(id, operator, model, immatriculation, capacity)
VALUES(NEXTVAL('planes_sequence_in_database'), 'Boeing', 'BOEING CV2', 'F-AZER', 15);

INSERT INTO planes(id, operator, model, immatriculation, capacity)
VALUES(NEXTVAL('planes_sequence_in_database'), 'Air Europa', '	B738', 'EC-MPS', 192);

INSERT INTO planes(id, operator, model, immatriculation, capacity)
VALUES(NEXTVAL('planes_sequence_in_database'),'Volotea','Airbus A320-216','EC-NOS',186);

COMMIT;