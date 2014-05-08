--// family_and_enterprise
-- Migration SQL that makes the change goes here.
CREATE TABLE Family
(
  contact integer NOT NULL PRIMARY KEY,
  lastName1 VARCHAR(255),
  lastName2 VARCHAR(255)
);

ALTER TABLE Family ADD FOREIGN KEY (contact) REFERENCES Contact(id);

CREATE TABLE Company
(
  contact integer NOT NULL PRIMARY KEY
);

ALTER TABLE Company ADD FOREIGN KEY (contact) REFERENCES Contact(id);

ALTER TABLE Person ADD 
(
  family integer,
  company integer
);

ALTER TABLE Person ADD FOREIGN KEY (family) REFERENCES Family(contact);
ALTER TABLE Person ADD FOREIGN KEY (company) REFERENCES Company(contact);


--//@UNDO
-- SQL to undo the change goes here.
ALTER TABLE Person DROP COLUMN family;
ALTER TABLE Person DROP COLUMN company;

DROP TABLE Family;
DROP TABLE Company;

