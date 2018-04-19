# natflax
Database management software for a book and movie rental store.

## Database Creation SQL
```
CREATE TABLE Employee (ID varchar(50) PRIMARY KEY, firstName varchar(20) NOT NULL, lastName varchar(20) NOT NULL, phone nchar(20), hours float NOT NULL, wage decimal(10,2) NOT NULL);

CREATE TABLE Store (SID varchar(50) PRIMARY KEY, address varchar(100) NOT NULL, phone nchar(20) NOT NULL, manager_ID varchar(50), FOREIGN KEY (manager_ID) REFERENCES Employee(ID));

CREATE TABLE Book (ISBN nchar(13) PRIMARY KEY, title varchar(100) NOT NULL, author varchar(100) NOT NULL, yearPublish year NOT NULL, rentFee decimal(100,2), replaceFee decimal(100,2));

CREATE TABLE Movie (ISAN nchar(13) PRIMARY KEY, title varchar(100) NOT NULL, director varchar(100) NOT NULL, yearRelease year NOT NULL, rentFee decimal(100,2), replaceFee decimal(100,2));

CREATE TABLE Customer (CID varchar(50) PRIMARY KEY, password varchar(50), username varchar(20) NOT NULL, address varchar(100), birthday date, phone nchar(20), firstName varchar(20) NOT NULL, lastName varchar(20) NOT NULL);

CREATE TABLE Payment (CID varchar(50) PRIMARY KEY, ccNumber nchar(16) NOT NULL, ccExpiration date NOT NULL, ccPIN nchar(4) NOT NULL, ccSecurity nchar(3) NOT NULL);

CREATE TABLE Rented_Books (CID varchar(50), ISBN nchar(13), SID varchar(50), rented_date date, FOREIGN KEY (CID) REFERENCES Customer(CID), FOREIGN KEY (ISBN) REFERENCES Book(ISBN), FOREIGN KEY (SID) REFERENCES Store(SID), PRIMARY KEY(CID,ISBN));

CREATE TABLE Rented_Movies (CID varchar(50), ISAN nchar(13), SID varchar(50), rented_date date, FOREIGN KEY (CID) REFERENCES Customer(CID), FOREIGN KEY (ISAN) REFERENCES Movie(ISAN), FOREIGN KEY (SID) REFERENCES Store(SID), PRIMARY KEY(CID,ISAN));

CREATE TABLE Books_in_Stock(ISBN nchar(13), SID varchar(50), stock integer NOT NULL, FOREIGN KEY (ISBN) REFERENCES Book(ISBN), FOREIGN KEY (SID) REFERENCES Store(SID), PRIMARY KEY(ISBN,SID));

CREATE TABLE Movies_in_Stock(ISAN nchar(13), SID varchar(50), stock integer NOT NULL, FOREIGN KEY (ISAN) REFERENCES Movie(ISAN), FOREIGN KEY (SID) REFERENCES Store(SID), PRIMARY KEY(ISAN,SID));

CREATE TABLE Works_for(ID varchar(50), SID varchar(50), FOREIGN KEY (ID) REFERENCES Employee(ID), FOREIGN KEY (SID) REFERENCES Store(SID), PRIMARY KEY(ID));
```
