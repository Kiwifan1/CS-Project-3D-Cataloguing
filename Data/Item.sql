-- Database holding 3D Asset Information

-- Things like "Sci-fi", "Fantasy", etc.
CREATE TABLE Class
(
    Name VARCHAR(50) NOT NULL,
    PRIMARY KEY (Name)
)

-- Things like "Weapon", "Armor", "Building", etc.
CREATE TABLE Type
(
    Name VARCHAR(50) NOT NULL,
    PRIMARY KEY (Name)
)

-- Things like "Sword", "Goblin", "Dragon", etc.
CREATE TABLE Entity
(
    Name VARCHAR(50) NOT NULL,
    PRIMARY KEY (Name)
)

-- The Folder that the 3D Assets are in
CREATE TABLE Release
(
    ID INT IDENTITY NOT NULL,
    Publisher VARCHAR(50) NOT NULL,
    Source VARCHAR(50) NOT NULL,
    PRIMARY KEY (ID)
)

-- The 3D Asset metadata, including the Release it belongs to
CREATE TABLE _File
(
    ID INT IDENTITY NOT NULL,
    FilePath VARCHAR(50) NOT NULL,
    ReleaseID INT NOT NULL,
    Image VARBINARY(MAX) NOT NULL,
    DownloadDate DATETIME NOT NULL,
    EditDate DATETIME CHECK (EditDate IS NULL OR EditDate > DownloadDate),
    PRIMARY KEY (Name, ReleaseID),
    FOREIGN KEY (ReleaseID) REFERENCES Release(ID)
)

-- The 3D Asset itself
Create Table Asset
(
    ID INT IDENTITY NOT NULL,
    FileID INT NOT NULL,
    ReleaseID INT NOT NULL,
    ClassName VARCHAR(50) NOT NULL,
    TypeName VARCHAR(50) NOT NULL,
    EntityName VARCHAR(50) NOT NULL,

    PRIMARY KEY (ID),
    FOREIGN KEY (FileID) REFERENCES _File(ID),
    FOREIGN KEY (ReleaseID) REFERENCES _File(ReleaseID),
    FOREIGN KEY (ClassName) REFERENCES Class(Name),
    FOREIGN KEY (TypeName) REFERENCES Type(Name),
    FOREIGN KEY (EntityName) REFERENCES Entity(Name)
)