CREATE TABLE Albums(
    isrc VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    contentDesc VARCHAR(255),
    releasedYear YEAR NOT NULL,
    artistFirstName VARCHAR(255) NOT NULL,
    artistLastName VARCHAR(255) NOT NULL,
    CONSTRAINT PK_isrc PRIMARY KEY(isrc),
    UNIQUE (isrc)
);

CREATE TABLE Images(
	img_id INT NOT NULL AUTO_INCREMENT,
    coverImage LONGBLOB,
    coverImageMIMEType VARCHAR(255),
    isrc VARCHAR(255) NOT NULL,
    CONSTRAINT PK_img_id PRIMARY KEY (img_id),
    CONSTRAINT FK_isrc FOREIGN KEY (isrc) REFERENCES Albums(isrc),
	UNIQUE (isrc)
);

CREATE TABLE Log(
   log_id INT NOT NULL AUTO_INCREMENT,
   logged_time DATETIME,
   typeOfChange VARCHAR(255),
   recordKey VARCHAR(255),
   CONSTRAINT PK_log_id PRIMARY KEY (log_id)
);