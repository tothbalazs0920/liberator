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

INSERT INTO Product (ID, Price, Currency, site, description, productType, POSTEDAT)
    VALUES ('2348b997-00b1-4dfe-ad2d-f76f88e227db', '12', 'DKK', 'dba', 'description', 'kemper', '2015-06-16');
    select exists(select 1 from product where id=2348b997-00b1-4dfe-ad2d-f76f88e227db)