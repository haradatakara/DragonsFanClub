
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
	product_id int NOT NULL UNIQUE AUTO_INCREMENT,
	name varchar(100) NOT NULL,
	price int NOT NULL,
	img varchar(100) NOT NULL,
	shop_id int NOT NULL,
	style_id int NOT NULL,
	genre_id int NOT NULL,
	PRIMARY KEY (product_id)
);
INSERT INTO products(name, price, img, shop_id, style_id, genre_id ) VALUES
('ポロラルフローレン', 3500, '/img/shop/nyuyoak/products/', 1, 2, 1),
('nautica', 7600, '/img/shop/flamingo/products/',2, 1, 2),
('gap', 9800, '/img/shop/amber/products/', 3, 2, 2),
('アメリカ輸入品', 6700, '/img/shop/ootora/products/', 4, 1, 2),
('ポロラルフローレン', 1500, '/img/shop/safari/products/', 5, 2, 1);


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
	shop_img varchar(100) NOT NULL,
	url varchar(200) NOT NULL,
	description varchar(300) NOT NULL,
	map varchar(500) NOT NULL,
	PRIMARY KEY (shop_id)
);
INSERT INTO shop(shop_name, address, tel_number, shop_img, url, description, map) VALUES
('NEW YORK JOE', '吉祥寺', '08055554444', '/img/shop/nyuyoak' ,'nyu-yoku.jp', '不要になった衣類・靴・鞄・アクセサリーの処分に困り、今までゴミとして捨てていた方…。買取に出したくても、ハードルの高い査定に売る事を諦め、タンスの肥やしにされている方…。NEW YORK JOE EXCHANGEでは、そういった方々のお持ちになられている衣類・靴・鞄・アクセサリーの最終引き取り人となるべく、独自の査定システムで買取させて頂きます。ブランド・ノーブランド、年代も問わずに幅広い間口より買取を致しますので、店内には様々なスタイルの商品が多数取り揃います。また、店内商品の全てが10,000円以下。平均商品価格は2,000円となります。', 'https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3240.0197661124466!2d139.5783145!3d35.701131200000006!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x6018ee37ee1db4ad%3A0xfba6f2e9d85af040!2z77yu77yl77y377y577yv77yy77yr77yq77yv77yl77yl77y477yj77yo77yh77yu77yn77yl!5e0!3m2!1sja!2sjp!4v1654223550725!5m2!1sja!2sjp'),
('FLAMINGO', '下北沢', '09055554444', '/img/shop/flamingo' ,'flamingo.jp', '不要になった衣類・靴・鞄・アクセサリーの処分に困り、今までゴミとして捨てていた方…。買取に出したくても、ハードルの高い査定に売る事を諦め、タンスの肥やしにされている方…。NEW YORK JOE EXCHANGEでは、そういった方々のお持ちになられている衣類・靴・鞄・アクセサリーの最終引き取り人となるべく、独自の査定システムで買取させて頂きます。ブランド・ノーブランド、年代も問わずに幅広い間口より買取を致しますので、店内には様々なスタイルの商品が多数取り揃います。また、店内商品の全てが10,000円以下。平均商品価格は2,000円となります。', 'https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3241.580864030987!2d139.66487101744383!3d35.6626969!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x6018f333338af425%3A0x87aaf990ac3129f1!2z44OV44Op44Of44Oz44K0IOS4i-WMl-ayouW6lw!5e0!3m2!1sja!2sjp!4v1654235006556!5m2!1sja!2sjp"'),
('AMBER LION', '吉祥寺', '08066664444','/img/shop/amber' , 'tapps.jp','JR吉祥寺駅から徒歩約3分のところにあります。入口がわかりにくいので気をつけてくださいね！「AMBER LION」はアメリカ・ヨーロッパの古着を扱うお店で、色々な古着があり見ているだけでも楽しいです♪', 'https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3240.0107717970945!2d139.57634951520097!3d35.70135253647572!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x6018ee37f434a3b3%3A0xa291b25abcc99e6d!2sAMBER%20LION!5e0!3m2!1sja!2sjp!4v1654235343822!5m2!1sja!2sjp"'),
('Small Change', '高円寺', '08055557777', '/img/shop/small' ,'samll.jp','不要になった衣類・靴・鞄・アクセサリーの処分に困り、今までゴミとして捨てていた方…。買取に出したくても、ハードルの高い査定に売る事を諦め、タンスの肥やしにされている方…。NEW YORK JOE EXCHANGEでは、そういった方々のお持ちになられている衣類・靴・鞄・アクセサリーの最終引き取り人となるべく、独自の査定システムで買取させて頂きます。ブランド・ノーブランド、年代も問わずに幅広い間口より買取を致しますので、店内には様々なスタイルの商品が多数取り揃います。また、店内商品の全てが10,000円以下。平均商品価格は2,000円となります。', 'https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3240.0107717970945!2d139.57634951520097!3d35.70135253647572!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x6018ee37f434a3b3%3A0xa291b25abcc99e6d!2sAMBER%20LION!5e0!3m2!1sja!2sjp!4v1654235343822!5m2!1sja!2sjp"'),
('safari', '吉祥寺', '08055557777','/img/shop/safari' , 'sfari.jp','不要になった衣類・靴・鞄・アクセサリーの処分に困り、今までゴミとして捨てていた方…。買取に出したくても、ハードルの高い査定に売る事を諦め、タンスの肥やしにされている方…。NEW YORK JOE EXCHANGEでは、そういった方々のお持ちになられている衣類・靴・鞄・アクセサリーの最終引き取り人となるべく、独自の査定システムで買取させて頂きます。ブランド・ノーブランド、年代も問わずに幅広い間口より買取を致しますので、店内には様々なスタイルの商品が多数取り揃います。また、店内商品の全てが10,000円以下。平均商品価格は2,000円となります。', 'https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3240.0107717970945!2d139.57634951520097!3d35.70135253647572!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x6018ee37f434a3b3%3A0xa291b25abcc99e6d!2sAMBER%20LION!5e0!3m2!1sja!2sjp!4v1654235343822!5m2!1sja!2sjp"'),
('古着商大虎', '高円寺', '08055557777', '/img/shop/ootora' ,'nyul.jp','不要になった衣類・靴・鞄・アクセサリーの処分に困り、今までゴミとして捨てていた方…。買取に出したくても、ハードルの高い査定に売る事を諦め、タンスの肥やしにされている方…。NEW YORK JOE EXCHANGEでは、そういった方々のお持ちになられている衣類・靴・鞄・アクセサリーの最終引き取り人となるべく、独自の査定システムで買取させて頂きます。ブランド・ノーブランド、年代も問わずに幅広い間口より買取を致しますので、店内には様々なスタイルの商品が多数取り揃います。また、店内商品の全てが10,000円以下。平均商品価格は2,000円となります。', 'https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3240.0107717970945!2d139.57634951520097!3d35.70135253647572!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x6018ee37f434a3b3%3A0xa291b25abcc99e6d!2sAMBER%20LION!5e0!3m2!1sja!2sjp!4v1654235343822!5m2!1sja!2sjp"');


DROP TABLE tweet_table;
CREATE TABLE tweet_table
(
	comment_id int NOT NULL UNIQUE AUTO_INCREMENT,
	comment varchar(140) NOT NULL,
	created datetime NOT NULL,
	user_id int DEFAULT 100 NOT NULL,
	PRIMARY KEY (comment_id)
);
INSERT INTO tweet_table(comment, created, user_id) VALUES
('おはようございます！！！', '2022-05-26 18:12:44.413', 1),
('こんばんは！！！', '2022-05-26 20:12:44.413', 2),
('だるい', '2022-05-27 18:12:44.413', 1);


DROP TABLE usersInfo;
CREATE TABLE usersInfo
(
	user_id int NOT NULL UNIQUE AUTO_INCREMENT,
	user_name varchar(50) NOT NULL,
	mailaddress varchar(50) NOT NULL UNIQUE,
	password varchar(30) NOT NULL,
	created datetime NOT NULL,
	age_id int NOT NULL,
	user_img varchar(200) NOT NULL 
	PRIMARY KEY (user_id)
);

DROP TABLE size_stock;
CREATE TABLE size_stock
(
    product_id int NOT NULL UNIQUE ,
    l_size int NOT NULL,
    xl_size int NOT NULL,
    xxl_size int NOT NULL
 );
 INSERT INTO size_stock VALUES
 (1, 1, 0, 0),
 (2, 0, 1, 0),
 (3, 1, 2, 3),
 (4, 1, 0, 3),
 (5, 1, 1, 1);
 
 
DROP TABLE size_stock2;
CREATE TABLE size_stock2
(
    stock_id int NOT NULL UNIQUE AUTO_INCREMENT,
    product_id int NOT NULL ,
    size_id int  NOT NULL
 );
 INSERT INTO size_stock2(product_id, size_id) VALUES
 (1, 1),
 (2, 2),
 (3, 1),
 (3, 2),
 (3, 2),
 (3, 3),
 (3, 3),
 (3, 3),
 (4, 1),
 (4, 3),
 (4, 3),
 (4, 3),
 (5, 1),
 (5, 2),
 (5, 3);
 
DROP TABLE size_correct;
CREATE TABLE size_correct
(
    size_id int NOT NULL,
    size_name varchar(100) NOT NULL 
 );
 INSERT INTO size_correct VALUES
 (1,'L'),
 (2,'XL'),
 (3,'XXL');
 
 
DROP TABLE order_master;
CREATE TABLE order_master
(
	order_id int NOT NULL UNIQUE AUTO_INCREMENT,
	product_id int NOT NULL,
	payment varchar(100) NOT NULL,
	orderday timestamp NOT NULL,
	l_size int NOT NULL,
	xl_size int NOT NULL,
	xxl_size int NOT NULL,
	arriveday varchar(100),
	user_id int NOT NULL,
	PRIMARY KEY (order_id)
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



