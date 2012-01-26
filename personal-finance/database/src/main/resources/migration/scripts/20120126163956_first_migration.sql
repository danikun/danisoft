--// First migration.
-- Migration SQL that makes the change goes here.
CREATE TABLE ContactType 
(
  id INTEGER NOT NULL PRIMARY KEY,
  code CHAR(1) NOT NULL,
  displayName VARCHAR(255) NOT NULL
);

INSERT INTO ContactType values(1, 'P', 'Person');
INSERT INTO ContactType values(1, 'C', 'Company');
INSERT INTO ContactType values(1, 'F', 'Family');

CREATE TABLE Contact
(
  id integer NOT NULL PRIMARY KEY,
  name varchar(255) NOT NULL,
  contactType integer NOT NULL FOREIGN KEY REFERENCES ContactType(id),
  address varchar(255)
);

CREATE TABLE Person
(
  id integer NOT NULL PRIMARY KEY,
  contact integer NOT NULL FOREIGN KEY REFERENCES Contact(id)
)


--//@UNDO
-- SQL to undo the change goes here.
DROP TABLE Contact;
DROP TABLE ContactType;


