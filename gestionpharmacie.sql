-- SQL Script to create the gestionpharmacie database and its tables

-- Create the database
CREATE DATABASE IF NOT EXISTS gestionpharmacie;

-- Use the database
USE gestionpharmacie;

-- Create users table
CREATE TABLE IF NOT EXISTS users (
    user_role VARCHAR(50) NOT NULL,
    name VARCHAR(100) NOT NULL,
    Joiningdate DATE NOT NULL,
    mobile_number VARCHAR(20) NOT NULL,
    IdCardNo VARCHAR(20) NOT NULL,
    username VARCHAR(50) NOT NULL PRIMARY KEY,
    password VARCHAR(50) NOT NULL,
    Salary VARCHAR(20) NOT NULL
);

-- Create medicine table
CREATE TABLE IF NOT EXISTS medicine (
    MedicineID VARCHAR(50) NOT NULL,
    Name VARCHAR(100) NOT NULL,
    CompanyName VARCHAR(100) NOT NULL,
    MFGDate DATE NOT NULL,
    ExpiryDate DATE NOT NULL,
    MG VARCHAR(50) NOT NULL,
    Quantity VARCHAR(20) NOT NULL,
    PricePerUnit VARCHAR(20) NOT NULL,
    PRIMARY KEY (MedicineID, ExpiryDate)
);

-- Create out_of_stock table
CREATE TABLE IF NOT EXISTS out_of_stock (
    MedicineID VARCHAR(50) NOT NULL,
    Name VARCHAR(100) NOT NULL,
    ExpiryDate DATE NOT NULL,
    PRIMARY KEY (MedicineID, ExpiryDate)
);

-- Create bill table
CREATE TABLE IF NOT EXISTS bill (
    billID VARCHAR(50) NOT NULL PRIMARY KEY,
    billDate DATE NOT NULL,
    totalPaid DOUBLE NOT NULL,
    generatedBy VARCHAR(50) NOT NULL,
    FOREIGN KEY (generatedBy) REFERENCES users(username)
);

-- Insert a default admin user
INSERT INTO users (user_role, name, Joiningdate, mobile_number, IdCardNo, username, password, Salary)
VALUES ('Admin', 'Administrator', CURDATE(), '00000000000', '00000000000000', 'admin', 'admin', '0');