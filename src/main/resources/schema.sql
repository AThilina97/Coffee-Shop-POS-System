CREATE TABLE IF NOT EXISTS User(
    username VARCHAR(100) PRIMARY KEY ,
    fullname VARCHAR(100) NOT NULL ,
    password VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS Employee(
    id VARCHAR(100) PRIMARY KEY ,
    name VARCHAR(100) NOT NULL,
    address VARCHAR(300) NOT NULL ,
    gender ENUM('MALE','FEMALE') NOT NULL ,
    dob DATE NOT NULL,
    salary VARCHAR(100) NOT NULL,
    designation VARCHAR(100) NOT NULL

);

CREATE TABLE IF NOT EXISTS Picture(
        employee_id VARCHAR(20) PRIMARY KEY ,
        picture MEDIUMBLOB NOT NULL ,
        CONSTRAINT fk_picture FOREIGN KEY (employee_id) REFERENCES Employee (id)
);
CREATE TABLE IF NOT EXISTS Contact(
    employee_id VARCHAR(20) NOT NULL ,
    contact VARCHAR(100) NOT NULL ,
    CONSTRAINT uk_contact UNIQUE KEY (contact),
    CONSTRAINT pk_contact UNIQUE KEY (employee_id,contact)

);
CREATE TABLE IF NOT EXISTS Coffee(
    code VARCHAR(20) PRIMARY KEY,
    name VARCHAR(100) NOT NULL ,
    price INT NOT NULL
);
CREATE TABLE IF NOT EXISTS CoffeePic(
    coffee_code VARCHAR(20) PRIMARY KEY ,
    coffee_pic MEDIUMBLOB NOT NULL ,
    CONSTRAINT fk_coffee_pic FOREIGN KEY (coffee_code)REFERENCES Coffee(code)

);

