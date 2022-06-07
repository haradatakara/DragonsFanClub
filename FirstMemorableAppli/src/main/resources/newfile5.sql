
/* Drop Tables */

DROP TABLE like_table;
DROP TABLE tweet_table;
DROP TABLE usersInfo;
DROP TABLE age;
DROP TABLE products;
DROP TABLE product_genre;
DROP TABLE product_size;
DROP TABLE product_style;
DROP TABLE shop;




/* Create Tables */

CREATE TABLE age
(
	age_id int NOT NULL UNIQUE,
	age_num int NOT NULL UNIQUE,
	PRIMARY KEY (age_id)
);


CREATE TABLE like_table
(
	like_id int NOT NULL UNIQUE,
	user_id int NOT NULL,
	comment_id int NOT NULL,
	PRIMARY KEY (like_id)
);

DROP TABLE order_master;

CREATE TABLE order_master
(
	order_id int NOT NULL UNIQUE AUTO_INCREMENT,
	product_id int NOT NULL,
	payment varchar(100) NOT NULL,
	orderday timestamp NOT NULL,
	user_id int NOT NULL,
	PRIMARY KEY (order_id)
);


CREATE TABLE products
(
	prodtct_id int NOT NULL UNIQUE,
	name varchar(100) NOT NULL,
	price int NOT NULL,
	img varchar(100) NOT NULL,
	shop_id int NOT NULL,
	style_id int NOT NULL,
	genre_id int NOT NULL,
	size_id int NOT NULL,
	PRIMARY KEY (prodtct_id)
);


CREATE TABLE product_genre
(
	genre_id int NOT NULL UNIQUE,
	genre_name varchar(30) NOT NULL UNIQUE,
	PRIMARY KEY (genre_id)
);


CREATE TABLE product_size
(
	size_id int NOT NULL UNIQUE,
	size_name varchar(10) NOT NULL UNIQUE,
	PRIMARY KEY (size_id)
);


CREATE TABLE product_style
(
	style_id int NOT NULL UNIQUE,
	style_name varchar(30) NOT NULL UNIQUE,
	PRIMARY KEY (style_id)
);


CREATE TABLE shop
(
	shop_id int NOT NULL UNIQUE,
	shop_name varchar(50) NOT NULL,
	address varchar(200) NOT NULL,
	tel_number int NOT NULL,
	url varchar(200) NOT NULL,
	shop_img varchar(100) NOT NULL,
	description varchar(300),
	PRIMARY KEY (shop_id)
);


CREATE TABLE tweet_table
(
	comment_id int NOT NULL UNIQUE,
	comment varchar(140) NOT NULL,
	created datetime NOT NULL,
	user_img varchar(50),
	user_id int DEFAULT 100 NOT NULL,
	PRIMARY KEY (comment_id)
);


CREATE TABLE usersInfo
(
	user_id int NOT NULL UNIQUE,
	user_name varchar(50) NOT NULL,
	mailaddress varchar(50) NOT NULL UNIQUE,
	password varchar(30) NOT NULL,
	created datetime NOT NULL,
	age_id int NOT NULL,
	user_img int,
	PRIMARY KEY (user_id)
);



/* Create Foreign Keys */

ALTER TABLE usersInfo
	ADD FOREIGN KEY (age_id)
	REFERENCES age (age_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE products
	ADD FOREIGN KEY (genre_id)
	REFERENCES product_genre (genre_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE products
	ADD FOREIGN KEY (size_id)
	REFERENCES product_size (size_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE products
	ADD FOREIGN KEY (style_id)
	REFERENCES product_style (style_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE products
	ADD FOREIGN KEY (shop_id)
	REFERENCES shop (shop_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE like_table
	ADD FOREIGN KEY (comment_id)
	REFERENCES tweet_table (comment_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE tweet_table
	ADD FOREIGN KEY (user_id)
	REFERENCES usersInfo (user_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;



