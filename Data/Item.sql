DROP TABLE IF EXISTS Asset;
DROP TABLE IF EXISTS AssetFile;
DROP TABLE IF EXISTS AssetRelease;
DROP TABLE IF EXISTS AssetSet;
DROP TABLE IF EXISTS AssetGroup;
DROP TABLE IF EXISTS Attribute;
DROP TABLE IF EXISTS AuditLog;
DROP TABLE IF EXISTS Admin;
DROP TABLE IF EXISTS AppUser;

CREATE TABLE Attribute
(
    Name VARCHAR(50) NOT NULL,
    Description TINYTEXT,
    PRIMARY KEY (Name)
);

CREATE TABLE AssetGroup
(
    Name VARCHAR(50) NOT NULL,
    Publisher VARCHAR(50) NOT NULL,
    Description TINYTEXT,
    PRIMARY KEY (Name, Publisher),
    FOREIGN KEY (Publisher) REFERENCES AssetRelease(Publisher)
);

CREATE TABLE AssetSet
(
    Name VARCHAR(50) NOT NULL,
    GroupName VARCHAR(50) NOT NULL,
    Description TINYTEXT,
    PRIMARY KEY (Name, GroupName),
    FOREIGN KEY (GroupName) REFERENCES AssetGroup(Name)
);

CREATE TABLE AssetRelease
(
    Name VARCHAR(50) NOT NULL,
    PubDate DATE NOT NULL,
    Publisher VARCHAR(50) NOT NULL,
    Source VARCHAR(50) NOT NULL,
    PRIMARY KEY (Name)
);

CREATE TABLE AssetFile
(
    Path VARCHAR(100) NOT NULL,
    ReleaseName VARCHAR(50) NOT NULL,
    ImagePath VARCHAR(100),
    DownloadDate DATE NOT NULL,
    EditDate DATE CHECK (EditDate >= DownloadDate OR EditDate IS NULL),
    PRIMARY KEY (Path),
    FOREIGN KEY (ReleaseName) REFERENCES AssetRelease(Name)
);

CREATE TABLE Asset
(
    FilePath VARCHAR(100) NOT NULL,
    AttributeName VARCHAR(50) NOT NULL,
    Name VARCHAR(50) NOT NULL,
    Scale VARCHAR(10) NOT NULL,
    -- TODO: Fix format for scale
    PRIMARY KEY (FilePath, AttributeName),
    FOREIGN KEY (FilePath) REFERENCES AssetFile(Path),
    FOREIGN KEY (AttributeName) REFERENCES Attribute(Name),
);

CREATE TABLE AppUser
(
    Username VARCHAR(50) NOT NULL,
    Pass VARCHAR(50) NOT NULL,
    PRIMARY KEY(Username, Pass)
);

CREATE TABLE Admin
(
    Username VARCHAR(50) NOT NULL,
    PRIMARY KEY (Username),
    FOREIGN KEY (Username) REFERENCES AppUser(Username)
);

CREATE TABLE AuditLog
(
    Username VARCHAR(50) NOT NULL,
    Action VARCHAR(50) NOT NULL,
    Date DATETIME NOT NULL,
    PRIMARY KEY (Username, Action, Date),
    FOREIGN KEY (Username) REFERENCES AppUser(Username)
);