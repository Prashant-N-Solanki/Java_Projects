CREATE TABLE address_book(
  person_id 	   SERIAL,
  person_name    VARCHAR(50),
  person_phone   VARCHAR(50),
  person_email   VARCHAR(50),
  person_address VARCHAR(50),
	
  PRIMARY KEY(person_id)
);

COPY address_book(person_name, person_phone, person_email, person_address)
FROM '<path_to_SampleContacts.csv_file>'
DELIMITER ','
CSV HEADER;