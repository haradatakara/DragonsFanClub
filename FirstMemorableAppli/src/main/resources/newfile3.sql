
/* Create Tables */

DROP TABLE age;
CREATE TABLE age
(
	age_id int NOT NULL UNIQUE,
	age_num int NOT NULL UNIQUE,
	PRIMARY KEY (age_id)
);


DROP TABLE comment;
CREATE TABLE comment
(
	id int NOT NULL UNIQUE,
	comment varchar(140) NOT NULL,
	created datetime NOT NULL,
	user_img varchar(50),
	user_id int DEFAULT 100 NOT NULL,
	PRIMARY KEY (id)
);


DROP TABLE peatcher_record;
CREATE TABLE peatcher_record
(
	palyer_id int NOT NULL UNIQUE,
	win int,
	lose int,
	era double,
	PRIMARY KEY (palyer_id)
);


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
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE comment
	ADD FOREIGN KEY (user_id)
	REFERENCES usersInfo (user_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;



