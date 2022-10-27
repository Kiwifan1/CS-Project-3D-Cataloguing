
-- Holds the attribute information, such that an asset can have multiple attributes
CREATE TABLE Attribute
(
    Name VARCHAR(50) NOT NULL,
    Description TINYTEXT,
    PRIMARY KEY (Name)
)

-- Each set must belong to a group
CREATE TABLE AssetGroup
(
    Name VARCHAR(50) NOT NULL,
    Description TINYTEXT,
    PRIMARY KEY (Name)
)

-- Each Asset must belong to a set
CREATE TABLE AssetSet
(
    Name VARCHAR(50) NOT NULL,
    GroupName VARCHAR(50) NOT NULL,
    Description TINYTEXT,
    PRIMARY KEY (Name, GroupName),
    FOREIGN KEY (GroupName) REFERENCES AssetGroup(Name)
)

-- All asset files must have come from a release of some kind 
CREATE TABLE Release
(
    ID INT NOT NULL,
    Name VARCHAR(50) NOT NULL,
    Date DATETIME NOT NULL,
    Publisher VARCHAR(50) NOT NULL,
    Source VARCHAR(50) NOT NULL,
    PRIMARY KEY (ID)
)

-- The metadata of the asset, including it's file path and download date
CREATE TABLE AssetFile
(
    ID INT NOT NULL,
    ReleaseID INT NOT NULL,
    Path VARCHAR(100) NOT NULL,
    Image VARBINARY(MAX) NOT NULL,
    DownloadDate DATETIME NOT NULL,
    EditDate DATETIME CHECK (EditDate >= DownloadDate OR EditDate IS NULL),
    PRIMARY KEY (ID),
    FOREIGN KEY (ReleaseID) REFERENCES Release(ID)
)

-- The actual asset, with information about it's name, attributes, and scale
CREATE TABLE Asset
(
    FileID INT NOT NULL,
    AttributeName VARCHAR(50) NOT NULL,
    Name VARCHAR(50) NOT NULL,
    SetName VARCHAR(5) NOT NULL,
    Scale VARCHAR(10) NOT NULL,
    -- TODO: Fix format for scale
    PRIMARY KEY (FileID, AttributeName),
    FOREIGN KEY (FileID) REFERENCES AssetFile(ID),
    FOREIGN KEY (AttributeName) REFERENCES Attribute(Name),
)