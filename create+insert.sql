CREATE TABLE MsRestaurant(
	RestaurantID INT PRIMARY KEY AUTO_INCREMENT,
	RestaurantName VARCHAR(100) NOT NULL,
	RestaurantBranch ENUM('Bandung', 'Jakarta', 'Bali', 'Surabaya', 'Samarinda', 'Padang') NOT NULL
);

CREATE TABLE MsEmployee(
	EmployeeID CHAR(6) PRIMARY KEY CHECK(EmployeeID REGEXP '^EMP[0-9]{3}$'),
	EmployeeName VARCHAR(100) NOT NULL,
	RestaurantID INT NOT NULL,
	FOREIGN KEY(RestaurantID) REFERENCES MsRestaurant(RestaurantID)
);

CREATE TABLE ReservationHeader(
	ReservationID INT PRIMARY KEY AUTO_INCREMENT,
	RestaurantID INT NOT NULL,
	EmployeeID CHAR(6) NOT NULL,
	CustomerName VARCHAR(100) NOT NULL,
	NumberOfTable INT NOT NULL,
	TableType ENUM('Romantic', 'General', 'Family') NOT NULL,
	NumberOfPeople INT NOT NULL,
	ReservationStatus ENUM('InOrder', 'InReserve', 'Finalized') NOT NULL,
	FOREIGN KEY(RestaurantID) REFERENCES MsRestaurant(RestaurantID),
	FOREIGN KEY(EmployeeID) REFERENCES MsEmployee(EmployeeID)
);

CREATE TABLE MsMenu(
	MenuID INT PRIMARY KEY AUTO_INCREMENT,
	RestaurantID INT NOT NULL,
	MenuCategory ENUM('Special', 'Local', 'Regular') NOT NULL,
	MenuName VARCHAR(100) NOT NULL,
	Price BIGINT NOT NULL,
	FOREIGN KEY(RestaurantID) REFERENCES MsRestaurant(RestaurantID) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE ReservationDetail(
	ReservationID INT,
	MenuID INT,
	Quantity INT NOT NULL,
	PRIMARY KEY(ReservationID, MenuID),
	FOREIGN KEY(ReservationID) REFERENCES ReservationHeader(ReservationID),
	FOREIGN KEY(MenuID) REFERENCES MsMenu(MenuID) ON UPDATE RESTRICT ON DELETE RESTRICT
);

CREATE TABLE SpecialMenuDetail(
	MenuID INT PRIMARY KEY,
	MenuStory VARCHAR(255) NOT NULL,
	FOREIGN KEY(MenuID) REFERENCES MsMenu(MenuID) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE LocalMenuDetail(
	MenuID INT PRIMARY KEY,
	UniqueTraits VARCHAR(100) NOT NULL,
	Origin VARCHAR(50) NOT NULL,
	FOREIGN KEY(MenuID) REFERENCES MsMenu(MenuID) ON UPDATE CASCADE ON DELETE CASCADE
);


INSERT INTO MsRestaurant(RestaurantName, RestaurantBranch)
VALUES
('Surabaya Restaurant', 'Surabaya'),
('Bandung Restaurant', 'Bandung'),
('Jakarta Restaurant', 'Jakarta'),
('Samarinda Restaurant', 'Samarinda'),
('Padang Restaurant', 'Padang'),
('Kuta Restaurant', 'Bali');


INSERT INTO MsMenu(RestaurantID, MenuCategory, MenuName, Price)
VALUES
(1, 'Local', 'Sate Klopo', 35000),
(2, 'Special', 'Bubur Ayam Kencur', 25000),
(3, 'Special', 'Nasi Goreng Jakarta', 40000),
(4, 'Local', 'Pecel Lele Manis Pedas', 30000),
(5, 'Local', 'Rendang', 50000),
(6, 'Special', 'Bebek Goreng', 45000),
(3, 'Regular', 'Rendang Fusion Jakarta', 45000),
(2, 'Regular', 'Soto Bandung Berkat Kencana', 30000),
(6, 'Regular', 'Bebek Legong Bali', 55000),
(1, 'Regular', 'Lontong Balap Krembangan', 20000),
(4, 'Regular', 'Pecel Lele Mahakam', 25000),
(5, 'Regular', 'Gulai Pesisir Padang', 30000);

INSERT INTO SpecialMenuDetail
VALUES
(3, 'Rendang Fusion Jakarta, perpaduan rendang Padang dan rempah Jakarta. Daging sapi empuk, bumbu rendang kental, dan sentuhan rempah khas Jakarta menciptakan pengalaman kuliner unik'),
(2, 'Soto Bandung Berkat Kencana, soto khas Bandung dengan kuah bening, kencur segar, daging ayam suwir, tauge, dan ketupat lembut. Harmoni rasa hangat dan rempah kencur khas Bandung.'),
(6, 'Bebek Betutu Legong Bali, keajaiban tradisional Bali. Bebek utuh dipanggang dengan rempah Bali, dibungkus daun pisang, menciptakan hidangan lezat dan eksotis yang merayakan keindahan Pulau Dewata.');

INSERT INTO LocalMenuDetail VALUES (1, 'Lontong balap khas Surabaya, nasi ketupat, taoge, tahu goreng, bumbu kacang, dan petis.', 'Krembangan'),
(4, 'Lele goreng saus pedas, paduan gurih lele dan saus yang memikat.', 'Mahakan'),
(5, 'Gulai daging santan kental, rempah Padang, aroma kuah pesisir yang khas.', 'Pesisir Padang')
