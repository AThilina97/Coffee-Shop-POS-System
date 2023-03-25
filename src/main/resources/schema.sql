CREATE TABLE IF NOT EXISTS User(
    username VARCHAR(100) PRIMARY KEY ,
    fullname VARCHAR(100) NOT NULL ,
    password VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS Employers(
    id VARCHAR(100) PRIMARY KEY ,
    name VARCHAR(100) NOT NULL,
    address VARCHAR(300) NOT NULL ,
    gender ENUM('MALE','FEMALE') NOT NULL ,
    dob DATE NOT NULL

);
CREATE TABLE IF NOT EXISTS Picture(
        employer_id VARCHAR(20) PRIMARY KEY ,
        picture MEDIUMBLOB NOT NULL ,
        CONSTRAINT fk_picture FOREIGN KEY (employer_id) REFERENCES Employers (id)
);
CREATE TABLE IF NOT EXISTS Contact(
    employer_id VARCHAR(20) NOT NULL ,
    contact VARCHAR(100) NOT NULL ,
    CONSTRAINT uk_contact UNIQUE KEY (contact),
    CONSTRAINT pk_contact UNIQUE KEY (employer_id,contact)

);

