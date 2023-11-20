-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
INSERT INTO planes(id, operator, model, registration, capacity)
VALUES(NEXTVAL('planes_sequence_in_database'), 'AirbusIndustrie', 'AIRBUS A380',
 'F-ABCD', 10);
INSERT INTO planes(id, operator, model, registration, capacity)
VALUES(NEXTVAL('planes_sequence_in_database'), 'Boeing', 'BOEING CV2', 'F-AZER', 15);