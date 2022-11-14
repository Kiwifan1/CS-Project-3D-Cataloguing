DROP TABLE IF EXISTS Asset;
DROP TABLE IF EXISTS AssetFile;
DROP TABLE IF EXISTS AssetGroup;
DROP TABLE IF EXISTS AssetRelease;
DROP TABLE IF EXISTS Attribute;
DROP TABLE IF EXISTS AuditLog;
DROP TABLE IF EXISTS Admin;
DROP TABLE IF EXISTS AppUser;


-- An attribute is a property of an asset, such as 'Goblin' or 'Humanoid'
CREATE TABLE Attribute
(
    Name VARCHAR(50) NOT NULL,
    Description TINYTEXT,
    PRIMARY KEY (Name)
);

-- An asset release is a collection of assets that are released together
CREATE TABLE AssetRelease
(
    Publisher VARCHAR(50) NOT NULL,
    Name VARCHAR(50) NOT NULL,
    PubDate DATE NOT NULL,
    Source VARCHAR(50) NOT NULL,
    PRIMARY KEY (Publisher)
);

-- An asset group is a collection of assets that are related to each other, there can be multiple asset groups per release
CREATE TABLE AssetGroup
(
    Name VARCHAR(50) NOT NULL,
    Publisher VARCHAR(50) NOT NULL,
    Description TINYTEXT,
    PRIMARY KEY (Name, Publisher),
    FOREIGN KEY (Publisher) REFERENCES AssetRelease(Publisher)
);

-- An asset file is the metadata for an asset
CREATE TABLE AssetFile
(
    Path VARCHAR(100) NOT NULL,
    Publisher VARCHAR(50) NOT NULL,
    ImagePath VARCHAR(100),
    DownloadDate DATE NOT NULL,
    EditDate DATE CHECK (EditDate >= DownloadDate OR EditDate IS NULL),
    PRIMARY KEY (Path),
    FOREIGN KEY (Publisher) REFERENCES AssetRelease(Publisher)
);

-- An asset is the actual asset object
CREATE TABLE Asset
(
    FilePath VARCHAR(100) NOT NULL,
    AttributeName VARCHAR(50) NOT NULL,
    Name VARCHAR(50) NOT NULL,
    Scale VARCHAR(10) NOT NULL,
    -- TODO: Fix format for scale
    PRIMARY KEY (FilePath, AttributeName),
    FOREIGN KEY (FilePath) REFERENCES AssetFile(Path),
    FOREIGN KEY (AttributeName) REFERENCES Attribute(Name)
);

-- An App user is a user of the application
CREATE TABLE AppUser
(
    Username VARCHAR(50) NOT NULL,
    Pass VARCHAR(50) NOT NULL,
    PRIMARY KEY(Username, Pass)
);

-- An admin is a user that has admin privileges
CREATE TABLE Admin
(
    Username VARCHAR(50) NOT NULL,
    PRIMARY KEY (Username),
    FOREIGN KEY (Username) REFERENCES AppUser(Username)
);

-- An audit log is a log of all actions performed by a user
CREATE TABLE AuditLog
(
    Username VARCHAR(50) NOT NULL,
    Action VARCHAR(50) NOT NULL,
    Time DATETIME NOT NULL,
    PRIMARY KEY (Username, Action, Time),
    FOREIGN KEY (Username) REFERENCES AppUser(Username)
);