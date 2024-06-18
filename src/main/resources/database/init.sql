SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS favorite_artwork;
DROP TABLE IF EXISTS artwork;
DROP TABLE IF EXISTS member_gallery;
DROP TABLE IF EXISTS notice;
DROP TABLE IF EXISTS member;
DROP TABLE IF EXISTS qna;




/* Create Tables */

CREATE TABLE artwork
(
	aseq int unsigned NOT NULL AUTO_INCREMENT,
	name varchar(45) NOT NULL,
	category varchar(45) NOT NULL,
	artist varchar(45) NOT NULL,
	year varchar(4) NOT NULL,
	material varchar(45) NOT NULL,
	size varchar(45) NOT NULL,
	displayyn char(1) DEFAULT 'Y' NOT NULL,
	content text NOT NULL,
	image varchar(100) NOT NULL,
	savefilename varchar(200) NOT NULL,
	indate datetime DEFAULT NOW(),
	PRIMARY KEY (aseq)
);


CREATE TABLE favorite_artwork
(
	id varchar(45) NOT NULL,
	aseq int unsigned NOT NULL,
	writedate datetime DEFAULT NOW() NOT NULL
);


CREATE TABLE member
(
	id varchar(45) NOT NULL,
	name varchar(45) NOT NULL,
	pwd varchar(45) NOT NULL,
	email varchar(45) NOT NULL,
	indate datetime DEFAULT NOW() NOT NULL,
	phone varchar(45) NOT NULL,
	adminyn char DEFAULT 'N' NOT NULL,
	PRIMARY KEY (id)
);


CREATE TABLE member_gallery
(
	mseq int unsigned NOT NULL AUTO_INCREMENT,
	author varchar(45) NOT NULL,
	title varchar(100) NOT NULL,
	writedate datetime DEFAULT NOW() NOT NULL,
	content text NOT NULL,
	readcount int unsigned DEFAULT 0 NOT NULL,
	image varchar(100) NOT NULL,
	savefilename varchar(200) NOT NULL,
	PRIMARY KEY (mseq)
);


CREATE TABLE notice
(
	nseq int unsigned NOT NULL AUTO_INCREMENT,
	title varchar(100) NOT NULL,
	author varchar(45) NOT NULL,
	writedate datetime DEFAULT NOW() NOT NULL,
	content text NOT NULL,
	readcount int unsigned DEFAULT 0 NOT NULL,
	category varchar(45) NOT NULL,
	PRIMARY KEY (nseq)
);


CREATE TABLE qna
(
	qseq int unsigned NOT NULL AUTO_INCREMENT,
	title varchar(100) NOT NULL,
	content text NOT NULL,
	writedate datetime DEFAULT NOW() NOT NULL,
	email varchar(45) NOT NULL,
	pwd varchar(45),
	phone varchar(45),
	reply text,
	publicyn char DEFAULT 'N' NOT NULL,
	PRIMARY KEY (qseq)
);



/* Create Foreign Keys */

ALTER TABLE favorite_artwork
	ADD FOREIGN KEY (aseq)
	REFERENCES artwork (aseq)
	ON UPDATE CASCADE
	ON DELETE CASCADE
;


ALTER TABLE favorite_artwork
	ADD FOREIGN KEY (id)
	REFERENCES member (id)
	ON UPDATE CASCADE
	ON DELETE CASCADE
;


ALTER TABLE member_gallery
	ADD FOREIGN KEY (author)
	REFERENCES member (id)
	ON UPDATE CASCADE
	ON DELETE CASCADE
;


ALTER TABLE notice
	ADD FOREIGN KEY (author)
	REFERENCES member (id)
	ON UPDATE CASCADE
	ON DELETE CASCADE
;



