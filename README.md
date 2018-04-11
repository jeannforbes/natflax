# natflax
Database management software for a book and movie rental store.

## Database Creation SQL
`CREATE TABLE Employee (ID varchar(50) PRIMARY KEY, firstName varchar(20) NOT NULL, lastName varchar(20) NOT NULL, phone nchar(20), hours float NOT NULL, wage decimal(10,2) NOT NULL);

CREATE TABLE Store (SID varchar(50) PRIMARY KEY, address varchar(100) NOT NULL, phone nchar(20) NOT NULL);

CREATE TABLE Book (ISBN nchar(13) PRIMARY KEY, title varchar(100) NOT NULL, author varchar(100) NOT NULL, yearPublish year NOT NULL, rentFee decimal(100,2), replaceFee decimal(100,2));

CREATE TABLE Movie (ISAN nchar(13) PRIMARY KEY, title varchar(100) NOT NULL, director varchar(100) NOT NULL, yearRelease year NOT NULL, rentFee decimal(100,2), replaceFee decimal(100,2));

CREATE TABLE Customer (CID varchar(50) PRIMARY KEY, password varchar(50), username varchar(20) NOT NULL, address varchar(100), birthday date, phone nchar(20), firstName varchar(20) NOT NULL, lastName varchar(20) NOT NULL);

CREATE TABLE Payment (CID varchar(50) PRIMARY KEY, ccNumber nchar(16) NOT NULL, ccExpiration date NOT NULL, ccPIN nchar(4) NOT NULL, ccSecurity nchar(3) NOT NULL);`
