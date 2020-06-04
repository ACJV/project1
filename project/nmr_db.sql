/*CREATE DATABASE nmr_db;*/

USE nmr_db;

CREATE TABLE category (
	cat_id 			INT NOT NULL PRIMARY KEY	AUTO_INCREMENT,
    cat_price  		DOUBLE,
    cat_name 		VARCHAR(255) NOT NULL,
    cat_description	VARCHAR(255),
    model_name		VARCHAR(255),
    brand			VARCHAR(255)
    
) ENGINE = InnoDB;

INSERT INTO category VALUES
(1, 1500, 'Standard', 'Small campervan with room for 2', 'Compact Supreme DL', 'Adria'),
(2, 1800, 'Standard Plus', 'Campervan with room for 3', 'Sonic Plus i700 SL', 'Adria'),
(3, 2000, 'Comfort', 'The Globetrotter is a fine camper with room for 4', 'Globetrotter Avantage 2,3', 'Dethleffs'),
(4, 2150, 'Comfort Plus', 'Globetrotter with a 3,0L engine, for the extra kick. Room for 4', 'Globetrotter 3,0', 'Dethleffs'),
(5, 2300, 'Luxury', 'The trend comes with automatic gear and room for 5', 'Trend I 7017 Aut. gear', 'Dethleffs'),
(6, 2450, 'Luxury Plus', 'Our exquisite compatcline with all you can dream of - room for 5 people and plenty of hot water', 'Compactline 144LE', 'Carthago'),
(7, 2600, 'Deluxe', 'The best Dethleffs available. Room for 6 people, if you must', 'Globeline 6613 EB', 'Dethleffs'),
(8, 2899, 'Deluxe Plus', 'Dosent get any better. This is a bus-sized camper, with room for 8 people - and a party', 'Charisma 850L', 'Concorde');

CREATE TABLE vehicle (
	reg_number		VARCHAR(9)	 NOT NULL	PRIMARY KEY,
	cat_id			INT NOT NULL,
    year_stmp 		INT(4),
    odometer		INT(6),
    transmission	ENUM('Automatic','Manual'),
    fuel_type		ENUM('Petrol','Diesel'),
    operational		TINYINT DEFAULT(1),
    o_comment		VARCHAR(255),
    
    INDEX (cat_id),
    FOREIGN KEY(cat_id)
    REFERENCES category (cat_id) ON UPDATE CASCADE ON DELETE CASCADE
    
)ENGINE = InnoDB;

INSERT INTO vehicle VALUES
('DK12450', 1, 2001, 58060, 'Manual', 'Diesel', 1, ''),
('TA50987', 1, 2002, 94000, 'Manual', 'Diesel', 1, ''),
('AN65777', 1, 2003, 9800, 'Manual', 'Diesel', 1, ''),
('TC58400', 1, 2002, 46650, 'Manual', 'Diesel', 1, ''),
('XC45457', 2, 2004, 65447, 'Manual', 'Petrol', 1, ''),
('BA64110', 2, 2004, 23114, 'Manual', 'Petrol', 1, ''),
('AA44130', 2, 2005, 84000, 'Manual', 'Petrol', 1, ''),
('VC8894', 2, 2005, 105047, 'Manual', 'Petrol', 1, ''),
('TN60583', 3, 2006, 30244, 'Automatic', 'Diesel', 1, ''),
('YY5547', 3, 2007, 78001, 'Automatic', 'Diesel', 1, ''),
('MN4151', 3, 2006, 89466, 'Automatic', 'Diesel', 1, ''),
('AW65589', 3, 2008, 65401, 'Automatic', 'Diesel', 1, ''),
('BV88447', 4, 2008, 44711, 'Automatic', 'Diesel', 1, ''),
('TY44331', 4, 2009, 102501, 'Automatic', 'Diesel', 1, ''),
('CV32556', 4, 2010, 47100, 'Automatic', 'Diesel', 1, ''),
('XT09811', 4, 2008, 98000, 'Automatic', 'Diesel', 1, ''),
('JH55470', 5, 2010, 44711, 'Automatic', 'Petrol', 1, ''),
('HG44988', 5, 2009, 201544, 'Automatic', 'Petrol', 1, ''),
('XX44150', 5, 2010, 99811, 'Automatic', 'Petrol', 1, ''),
('NM99471', 5, 2011, 37540, 'Automatic', 'Petrol', 1, ''),
('ZW44329', 6, 2010, 44711, 'Automatic', 'Diesel', 1, ''),
('SD40091', 6, 2013, 88543, 'Automatic', 'Diesel', 1, ''),
('QA47778', 6, 2011, 108477, 'Automatic', 'Diesel', 1, ''),
('FT47551', 6, 2012, 240144, 'Automatic', 'Diesel', 1, ''),
('RY44755', 7, 2016, 77844, 'Manual', 'Diesel', 1, ''),
('EW40998', 7, 2017, 44775, 'Manual', 'Diesel', 1, ''),
('PO54367', 7, 2018, 201004, 'Manual', 'Diesel', 1, ''),
('PL89012', 7, 2016, 147533, 'Manual', 'Diesel', 1, ''),
('IT43129', 8, 2019, 47751, 'Automatic', 'Diesel', 1, ''),
('VV43288', 8, 2020, 12451, 'Automatic', 'Diesel', 1, ''),
('VW09981', 8, 2019, 67488, 'Automatic', 'Diesel', 1, ''),
('CR11008', 8, 2020, 1844, 'Automatic', 'Diesel', 1, '');

CREATE TABLE address (
	address_id			INT(10) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    address				VARCHAR(255),
    zip					VARCHAR(15),
    city				VARCHAR(255),
    country				VARCHAR(255),
    distance			INT(10)
    
)ENGINE = InnoDB;

INSERT INTO address VALUES
(1, 'Kastrup Industrivej 12', '2300', 'Kastrup', 'Denmark', 0),
(2, 'Storgade 48', 4180, 'Sorø', 'Denmark', 0),
(3, 'Nørrebrogade 44', 2200, 'Nørrebro', 'Denmark', 0),
(4, 'Amagerfælledvej 24', '2300', 'Amager', 'Denmark', 0),
(5, 'Kildegårdsvej 11', '2820', 'Gentofte', 'Denmark', 0);

CREATE TABLE customer (
	customer_id 	INT(10) NOT NULL PRIMARY KEY	AUTO_INCREMENT,
	full_name   	VARCHAR(255),
    driver_lic_no	VARCHAR(255),
    dob				DATE,
    phone_no	VARCHAR(30),
	email			VARCHAR(255),
    address_id		INT(10),
    
    INDEX (address_id),
    FOREIGN KEY (address_id)
    REFERENCES address (address_id) ON UPDATE CASCADE ON DELETE CASCADE
    
)ENGINE = InnoDB;

INSERT INTO customer VALUES
/*ID,  FULL NAME, DRIVERS LICENSE, DOB, PHONE NO, EMAIL, ADDRESS_ID */
(1, 'Christian Nymark', '0101-ASD-55', '1999-12-31', '55721578', 'chris@getsAgrade.dk', 2),
(2, 'Asthor Bragason', '0124-TY-55', '1985-02-11', '68457811', 'asthor@getsBgrade.dk', 3),
(3, 'Juste Dilyte', '78-TTT-22', '2005-08-19', '44887711', 'juste@getsCgrade.dk', 4),
(4, 'Vytautas Kiravelis', '784-JJI-144', '1980-01-02', '44775156', 'vytas@getsDgrade.dk', 5);

create table booking (
	booking_no INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    reg_number VARCHAR(9) NOT NULL,
    pick_up_date DATE NOT NULL,
    drop_off_date DATE NOT NULL,
    booking_status ENUM('Confirmed', 'Cancelled', 'Finished') DEFAULT ('Confirmed'),
    customer_id INT NOT NULL DEFAULT 0,
    pick_up_id INT NOT NULL DEFAULT 1,
    drop_off_id INT NOT NULL DEFAULT 1,
    bike_rack TINYINT DEFAULT 0,
    bed_linen INT DEFAULT 0,
    child_seat INT DEFAULT 0,
    total_price DOUBLE,
    
    INDEX (reg_number),
    FOREIGN KEY (reg_number)
    REFERENCES vehicle (reg_number) ON UPDATE CASCADE ON DELETE CASCADE,
    
    INDEX (pick_up_id),
    FOREIGN KEY (pick_up_id)
    REFERENCES address (address_id) ON UPDATE CASCADE ON DELETE CASCADE,
    
    INDEX (drop_off_id),
    FOREIGN KEY (drop_off_id)
    REFERENCES address (address_id) ON UPDATE CASCADE ON DELETE CASCADE
    
)ENGINE = InnoDB;