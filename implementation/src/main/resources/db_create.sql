CREATE TABLE Albums(
    isrc VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    contentDesc VARCHAR(255),
    releasedYear YEAR NOT NULL,
    artistFirstName VARCHAR(255) NOT NULL,
    artistLastName VARCHAR(255) NOT NULL,
    coverImage LONGBLOB,
    coverImageMIMEType VARCHAR(255),
    CONSTRAINT PK_isrc PRIMARY KEY(isrc),
    UNIQUE (isrc)
);

CREATE TABLE Log(
   log_id INT NOT NULL AUTO_INCREMENT,
   logged_time DATETIME,
   typeOfChange VARCHAR(255),
   recordKey VARCHAR(255),
   CONSTRAINT PK_log_id PRIMARY KEY (log_id)
);