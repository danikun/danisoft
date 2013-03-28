--// phoneNumber
-- Migration SQL that makes the change goes here.
CREATE TABLE PhoneNumberType 
(
  id INTEGER NOT NULL PRIMARY KEY,
  code CHAR(1) NOT NULL,
  displayName VARCHAR(255) NOT NULL
);

INSERT INTO PhoneNumberType values(1, 'M', 'Mobile');
INSERT INTO PhoneNumberType values(2, 'H', 'Home');
INSERT INTO PhoneNumberType values(3, 'C', 'Company');

CREATE TABLE PhoneNumber
(
  id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
  phonenumbertype INTEGER NOT NULL,
  phonenumber VARCHAR(25),
  contact INTEGER NOT NULL
);

ALTER TABLE PhoneNumber ADD FOREIGN KEY (contact) REFERENCES Contact(id);
ALTER TABLE PhoneNumber ADD FOREIGN KEY (phonenumbertype) REFERENCES PhoneNumberType(id);

--//@UNDO
-- SQL to undo the change goes here.
DROP TABLE PhoneNumber;
DROP TABLE PhoneNumberType;


