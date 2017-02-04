DROP TABLE IF EXISTS Product;

CREATE TABLE Product(
   ID VARCHAR(150) PRIMARY KEY,
   Price REAL,
   Currency VARCHAR(4),
   site VARCHAR(20),
   description VARCHAR,
   productType Varchar(20),
   POSTEDAT timestamp with time zone
);
