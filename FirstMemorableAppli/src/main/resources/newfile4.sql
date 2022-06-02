
/* Drop Tables */





/* Create Tables */

DROP TABLE age;
CREATE TABLE age
(
	age_id int NOT NULL UNIQUE AUTO_INCREMENT,
	age_num int NOT NULL UNIQUE,
	PRIMARY KEY (age_id)
);

INSERT INTO age(age_num) VALUES
(10),
(20),
(30),
(40),
(50);


DROP TABLE like_table;
CREATE TABLE like_table
(
	like_id int NOT NULL UNIQUE AUTO_INCREMENT,
	user_id int NOT NULL,
	comment_id int NOT NULL,
	PRIMARY KEY (like_id)
);
INSERT INTO like_table(user_id, comment_id) VALUES
(1,6),
(1,7),
(1,8),
(1,9);



DROP TABLE product_style;
DROP TABLE prodct_style;
CREATE TABLE product_style
(
	style_id int NOT NULL UNIQUE AUTO_INCREMENT,
	style_name varchar(30) NOT NULL UNIQUE,
	PRIMARY KEY (style_id)
);
INSERT INTO product_style(style_name) VALUES
('ストリート'),
('アメリカンカジュアル');




DROP TABLE products;
CREATE TABLE products
(
	prodtct_id int NOT NULL UNIQUE AUTO_INCREMENT,
	name varchar(100) NOT NULL,
	price int NOT NULL,
	img varchar(100) NOT NULL,
	shop_id int NOT NULL,
	style_id int NOT NULL,
	genre_id int NOT NULL,
	size_id int NOT NULL,
	PRIMARY KEY (prodtct_id)
);
INSERT INTO products(name, price, img, shop_id, style_id, genre_id, size_id) VALUES
('ポロラルフローレン', 3500, 'img/clothes/img1.jpg', 1, 2, 1, 2),
('nautica', 7600, 'img/clothes/img2.jpg',4, 1, 2, 2),
('gap', 9800, 'img/clothes/img3.jpg', 5, 2, 2, 2),
('アメリカ輸入品', 6700, 'img/clothes/img4.jpg', 3, 1, 2, 2),
('ポロラルフローレン', 1500, 'img/clothes/img5.jpg', 1, 2, 1, 2);


DROP TABLE product_genre;
CREATE TABLE product_genre
(
	genre_id int NOT NULL UNIQUE AUTO_INCREMENT,
	genre_name varchar(30) NOT NULL UNIQUE,
	PRIMARY KEY (genre_id)
);
INSERT INTO product_genre(genre_name) VALUES
('トップス'),
('ボトムス');


DROP TABLE product_size;
CREATE TABLE product_size
(
	size_id int NOT NULL UNIQUE AUTO_INCREMENT,
	size_name varchar(10) NOT NULL UNIQUE,
	PRIMARY KEY (size_id)
);
INSERT INTO product_size(size_name) VALUES
('L'),
('XL'),
('XXL'),
('XXXL');


DROP TABLE shop;
CREATE TABLE shop
(
	shop_id int NOT NULL UNIQUE AUTO_INCREMENT,
	shop_name varchar(50) NOT NULL,
	address varchar(200) NOT NULL,
	tel_number varchar(200) NOT NULL,
	url varchar(200) NOT NULL,
	PRIMARY KEY (shop_id)
);
INSERT INTO shop(shop_name, address, tel_number, url) VALUES
('ニューヨークジョー', '吉祥寺', '08055554444', 'http:nyu-yoku.jp'),
('フラミンゴ', '原宿', '09055554444', 'http:flamingo.jp'),
('タップス', '下北沢', '08066664444', 'http:tapps.jp'),
('make', '吉祥寺', '08055557777', 'http:nyullku.jp');


DROP TABLE tweet_table;
CREATE TABLE tweet_table
(
	comment_id int NOT NULL UNIQUE AUTO_INCREMENT,
	comment varchar(140) NOT NULL,
	created datetime NOT NULL,
	user_img varchar(50),
	user_id int DEFAULT 100 NOT NULL,
	PRIMARY KEY (comment_id)
);
INSERT INTO tweet_table(comment, created, user_img, user_id) VALUES
('おはようございます！！！', '2022-05-26 18:12:44.413', '/img/user/img01.jpg', 1),
('こんばんは！！！', '2022-05-26 20:12:44.413', '/img/user/img02.jpg', 2),
('だるい', '2022-05-27 18:12:44.413', '/img/user/img01.jpg', 1),
('おはようございます！！！', '2022-05-28 10:12:44.413', '/img/user/img03.jpg', 3),
('おはようございます！！！', '2022-05-29 11:12:44.413', '/img/user/img03.jpg', 3);

DROP TABLE usersInfo;
CREATE TABLE usersInfo
(
	user_id int NOT NULL UNIQUE AUTO_INCREMENT,
	user_name varchar(50) NOT NULL,
	mailaddress varchar(50) NOT NULL UNIQUE,
	password varchar(30) NOT NULL,
	created datetime NOT NULL,
	age_id int NOT NULL,
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
	ADD FOREIGN KEY (style_id)
	REFERENCES prodct_style (style_id)
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
	ADD FOREIGN KEY (shop_id)
	REFERENCES shop (shop_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE like
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



