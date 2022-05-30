
/* Drop Tables */

DROP TABLE usersInfo;
DROP TABLE age;




/* Create Tables */

CREATE TABLE age
(
	age_id int NOT NULL UNIQUE,
	age_num int NOT NULL UNIQUE,
	PRIMARY KEY (age_id)
);

DROP TABLE peatcher_record;

CREATE TABLE peatcher_record
(
	player_id int NOT NULL UNIQUE,
	win int,
	lose int,
	era double,
	PRIMARY KEY (player_id)
);

INSERT INTO peatcher_record VALUES 
(1, 10, 8, 2.34),
(2, 10, 8, 2.34),
(3, 10, 8, 2.34),
(4, 10, 8, 2.34),
(5, 10, 8, 2.34),
(6, 10, 8, 2.34),
(7, 10, 8, 2.34),
(8, 10, 8, 2.34);


ALTER TABLE peatcher_record
	ADD FOREIGN KEY (player_id)
	REFERENCES players_info (player_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


CREATE TABLE usersInfo
(
	user_id int DEFAULT 100 NOT NULL UNIQUE,
	user_name varchar(50),
	mailaddress varchar(50) NOT NULL UNIQUE,
	password varchar(30) NOT NULL,
	age int NOT NULL,
	created datetime NOT NULL,
	age_id int NOT NULL UNIQUE,
	PRIMARY KEY (user_id)
);



/* Create Foreign Keys */

ALTER TABLE usersInfo
	ADD FOREIGN KEY (age_id)
	REFERENCES age (age_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;



