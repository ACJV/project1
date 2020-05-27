DROP TABLE IF EXISTS category;

CREATE TABLE category (
	categoryID 		INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    cat_price  		DOUBLE,
    cat_name 		VARCHAR(255) NOT NULL,
    cat_description	VARCHAR(255),
    model_name		VARCHAR(255),
    brand			VARCHAR(255)
    
) ENGINE = InnoDB;

INSERT INTO category VALUES
/* CATEGORY ID - CATEGORY PRICE - CATEGORY NAME - CATEGORY DESCRIPTION - MODEL NAME - BRAND */
(1, 60000, 'First Category Name', 'This is the first category for testing purposes.', 'First Model', 'Test GTI');

-- =============================================

DROP TABLE IF EXISTS extras;

CREATE TABLE extras (
	extras_name			VARCHAR(255) NOT NULL	PRIMARY KEY,  
	extras_price		DOUBLE,
    extras_description	VARCHAR(255)

)ENGINE = InnoDB;

INSERT INTO extras VALUES 
/*EXTRAS NAME - EXTRAS PRICE - EXTRAS DESCRIPTION*/
('bike_rack', 150.00, 'Bike rack mounted on RV');

-- =============================================

DROP TABLE IF EXISTS address;

CREATE TABLE address (
	address_id			INT(10) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    address				VARCHAR(255),
    zip					INT(10),
    city				VARCHAR(255),
    country				VARCHAR(255),
    distance			INT(10)
    
)ENGINE = InnoDB;
    
INSERT INTO address VALUES
/* ADDRESS ID - ADDRESS - ZIP CODE - CITY - COUNTRY - DISTANCE */
(1, 'Test Road 1', 1010101010, 'Ten Test City', 'Trialland', 10);

-- =============================================

DROP TABLE IF EXISTS customer;

CREATE TABLE customer (
	customer_id 	INT(10) NOT NULL PRIMARY KEY	AUTO_INCREMENT,
    booking_id		INT(10),
	full_name   	VARCHAR(255),
    driver_license	VARCHAR(255),
    dob				DATE,
    phone_number	INT(20),
	email			VARCHAR(255),
    address_id		INT(10),
    
    INDEX (address_id),
    FOREIGN KEY (address_id)
    REFERENCES address (address_id) ON UPDATE CASCADE ON DELETE CASCADE
    
)ENGINE = InnoDB;

INSERT INTO customer VALUES
/*ID,  FULL NAME, DRIVERS LICENSE, DOB, PHONE NO, EMAIL, ADDRESS_ID */
(1, 1, 'Test Person', '0101-ASD-55', '1999-12-31', '12345678', 'test@person.dk', 1);

-- =============================================

DROP TABLE IF EXISTS vehicle;

CREATE TABLE vehicle (
	regNumber		VARCHAR(7)	 NOT NULL	PRIMARY KEY,
	categoryID		INT(1),
    yearStmp 		INT(4),
    odometer		INT(6),
    transmission	ENUM('automatic','manual'),
    fuelType		ENUM('petrol','diesel'),
    descriptionX	VARCHAR(255),
    operational		TINYINT,
    oComment		VARCHAR(255),
    
    INDEX (categoryID),
	FOREIGN KEY (categoryID)
	REFERENCES category (categoryID)  ON UPDATE CASCADE ON DELETE CASCADE

)ENGINE = InnoDB;

INSERT INTO vehicle VALUES
/* REG NUMBER - CATEGORY ID - YEAR - ODOMETER - TRANSMISSION - FUEL TYPE - AVAILABILITY - Avail. COMMENTS */
('XX12345', 1, 1999, 999999, 'automatic', 'petrol', 'This a sdescription', 0, 'This is a test, set to not avail.');

-- =============================================

DROP TABLE IF EXISTS booking;

CREATE TABLE booking (
	booking_id			INT(10) NOT NULL PRIMARY KEY	AUTO_INCREMENT,
    vehicle_reg_number	VARCHAR(7) NOT NULL,
    pick_up				DATE,
    drop_off			DATE,
    customer_id			INT(10),
    pick_up_id			INT(10),
    drop_off_id			INT(10),
    bike_rack			TINYINT,
    bed_linen			INT(1),
    child_seat			INT(1),
    price_total			DOUBLE,
    extras_name			VARCHAR(255),
    
    INDEX (vehicle_reg_number),
    FOREIGN KEY (vehicle_reg_number)
    REFERENCES vehicle (regNumber) ON UPDATE CASCADE ON DELETE CASCADE,
    
    INDEX (customer_id),
    FOREIGN KEY (customer_id)
    REFERENCES customer (customer_id) ON UPDATE CASCADE ON DELETE CASCADE
    
)ENGINE = InnoDB;

INSERT INTO booking VALUES
/* BOOKING ID - REG NUMBER - PICK UP - DROP OFF - CUSTOMER ID - PICK UP ID - DROP OFF ID - BIKE RACK - BED LINEN - CHILD SEAT - PRICE TOTAL - EXTRAS NAME */
(1, 'XX12345', '1999-12-31', '2000-01-31', 1, 1, 1, 0, 2, 1, 6500.00, '2 x bed linen, child seat');

