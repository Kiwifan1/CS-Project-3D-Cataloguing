DROP TABLE IF EXISTS Asset;
DROP TABLE IF EXISTS AuditLog;
DROP TABLE IF EXISTS Admin;
DROP TABLE IF EXISTS AssetRelease;
DROP TABLE IF EXISTS Publisher;
DROP TABLE IF EXISTS Attribute;
DROP TABLE IF EXISTS AppUser;
DROP TABLE IF EXISTS Scale;

CREATE TABLE Attribute
(
    name VARCHAR(50) NOT NULL,
    description TINYTEXT,
    PRIMARY KEY(name)
);

CREATE TABLE Scale
(
    name VARCHAR(50) NOT NULL,
    PRIMARY KEY(name)
);

CREATE TABLE Publisher
(
    name VARCHAR(50) NOT NULL,
    source VARCHAR(50) NOT NULL,
    PRIMARY KEY(name)
);

CREATE TABLE AssetRelease
(
    id INT NOT NULL,
    name VARCHAR(50) NOT NULL,
    publisher VARCHAR(50) NOT NULL,
    description TINYTEXT,
    PRIMARY KEY (id),
    FOREIGN KEY(publisher) REFERENCES Publisher(name)
);

CREATE TABLE AppUser
(
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    last_login DATETIME,
    PRIMARY KEY(username)
);

CREATE TABLE Admin
(
    username VARCHAR(50) NOT NULL,
    PRIMARY KEY(username),
    FOREIGN KEY (username) REFERENCES AppUser(username) ON DELETE CASCADE
);

CREATE TABLE AuditLog
(
    username VARCHAR(50) NOT NULL,
    time DATETIME NOT NULL,
    action TINYTEXT NOT NULL,
    PRIMARY KEY(username, time),
    FOREIGN KEY (username) REFERENCES AppUser(username)
);

CREATE TABLE Asset
(
    filepath VARCHAR(255) NOT NULL,
    attribute VARCHAR(50) NOT NULL,
    username VARCHAR(50) NOT NULL,
    name VARCHAR(50) NOT NULL,
    rid INT NOT NULL,
    scale VARCHAR(10) NOT NULL,
    description TINYTEXT,
    PRIMARY KEY(filepath, attribute),
    FOREIGN KEY(attribute) REFERENCES Attribute(name),
    FOREIGN KEY(rid) REFERENCES AssetRelease(id),
    FOREIGN KEY(username) REFERENCES AppUser(username),
    FOREIGN KEY(scale) REFERENCES Scale(name)
);
