--// First migration.
-- Migration SQL that makes the change goes here.
CREATE TABLE ContactType 
(
  id INTEGER NOT NULL PRIMARY KEY,
  code CHAR(1) NOT NULL,
  displayName VARCHAR(255) NOT NULL
);

INSERT INTO ContactType values(1, 'P', 'Person');
INSERT INTO ContactType values(2, 'C', 'Company');
INSERT INTO ContactType values(3, 'F', 'Family');

CREATE TABLE Contact
(
  id integer NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
  name varchar(255) NOT NULL,
  contactType integer NOT NULL,
  address varchar(255)
);

ALTER TABLE Contact ADD FOREIGN KEY (contactType) REFERENCES ContactType(id);

CREATE TABLE Person
(
  id integer NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
  contact integer NOT NULL,
  lastName1 VARCHAR(255),
  lastName2 VARCHAR(255)
);

ALTER TABLE Person ADD FOREIGN KEY (contact) REFERENCES Contact(id);


--//@UNDO
-- SQL to undo the change goes here.
DROP TABLE Person;
DROP TABLE Contact;
DROP TABLE ContactType;