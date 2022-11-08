-- Insert test data into tables

INSERT INTO Attribute
VALUES
    ('Goblin', 'Little green man'),
    ('Orc', 'Big Green man'),
    ('Humanoid', 'Humanoid'),
    ('Human', 'Human'),
    ('Elf', 'Elf'),
    ('Dwarf', 'Dwarf'),
    ('Halfling', 'Halfling'),
    ('Dragon', 'Dragon'),
    ('Giant', 'Giant')

INSERT INTO AssetRelease
VALUES
    ('D&D', 'Monster Manual', '2014-09-15', 'MyMiniFactory'),
    ('Bladesmith', 'Hunters & Hunted', '2015-02-13', 'Patreon'),
    ('Green Fury Goblins', 'Goblin Warrios', '2018-09-11', '3DModeling'),
    ('WhiteWolf', 'May Release', '2014-04-13', 'Patreon')

INSERT INTO AssetGroup
VALUES
    ('Goblins', 'D&D', 'Goblins'),
    ('Orcs', 'D&D', 'Orcs'),
    ('Dragons', 'D&D', 'Dragons'),
    ('Giant', 'D&D', 'Giant'),
    ('Goblins', 'Bladesmith', 'Goblins'),
    ('Orcs', 'Bladesmith', 'Orcs'),
    ('Dragons', 'Bladesmith', 'Dragons'),
    ('Giant', 'Bladesmith', 'Giant'),
    ('Humanoids', 'Bladesmith', 'Humans'),
    ('Elf', 'Bladesmith', 'Humans'),
    ('Dwarf', 'Bladesmith', 'Humans'),
    ('Halfling', 'Bladesmith', 'Humans'),
    ('Goblins', 'Green Fury Goblins', 'Goblins'),
    ('Orcs', 'Green Fury Goblins', 'Orcs'),
    ('Dragons', 'Green Fury Goblins', 'Dragons'),
    ('Giant', 'Green Fury Goblins', 'Giant'),
    ('Goblins', 'WhiteWolf', 'Goblins'),
    ('Orcs', 'WhiteWolf', 'Orcs'),
    ('Dragons', 'WhiteWolf', 'Dragons'),
    ('Giant', 'WhiteWolf', 'Giant')

INSERT INTO AssetFile
VALUES
    ('D&D/Goblin/Goblin.stl', 'D&D', 'D&D/Goblin/Goblin.png', '2014-09-15', '2014-09-16'),
    ('D&D/Goblin/Goblin2.stl', 'D&D', 'D&D/Goblin/Goblin2.png', '2014-09-15', '2014-09-16'),
    ('D&D/Goblin/Goblin3.stl', 'D&D', 'D&D/Goblin/Goblin3.png', '2014-09-15', '2014-09-16'),
    ('D&D/Goblin/Goblin4.stl', 'D&D', 'D&D/Goblin/Goblin4.png', '2014-09-15', '2014-09-16'),
    ('D&D/Goblin/Goblin5.stl', 'D&D', 'D&D/Goblin/Goblin5.png', '2014-09-15', '2014-09-16'),
    ('D&D/Goblin/Goblin6.stl', 'D&D', 'D&D/Goblin/Goblin6.png', '2014-09-15', '2014-09-16'),
    ('Bladesmith/Goblin/Goblin.stl', 'Bladesmith', 'Bladesmith/Goblin/Goblin.png', '2015-02-13', '2015-03-13'),
    ('Bladesmith/Goblin/Goblin2.stl', 'Bladesmith', 'Bladesmith/Goblin/Goblin2.png', '2015-02-13', '2015-03-13'),
    ('Bladesmith/Goblin/Goblin3.stl', 'Bladesmith', 'Bladesmith/Goblin/Goblin3.png', '2015-02-13', '2015-03-13'),
    ('Bladesmith/Goblin/Goblin4.stl', 'Bladesmith', 'Bladesmith/Goblin/Goblin4.png', '2015-02-13', '2015-03-13'),
    ('Bladesmith/Goblin/Goblin5.stl', 'Bladesmith', 'Bladesmith/Goblin/Goblin5.png', '2015-02-13', '2015-03-13'),
    ('Bladesmith/Goblin/Goblin6.stl', 'Bladesmith', 'Bladesmith/Goblin/Goblin6.png', '2015-02-13', '2015-03-13'),
    ('Green Fury Goblins/Goblin/Goblin.stl', 'Green Fury Goblins', 'Green Fury Goblins/Goblin/Goblin.png', '2018-09-11', '2018-010-15'),
    ('Green Fury Goblins/Goblin/Goblin2.stl', 'Green Fury Goblins', 'Green Fury Goblins/Goblin/Goblin2.png', '2018-09-11', '2018-010-15'),
    ('Green Fury Goblins/Goblin/Goblin3.stl', 'Green Fury Goblins', 'Green Fury Goblins/Goblin/Goblin3.png', '2018-09-11', '2018-010-15'),
    ('Green Fury Goblins/Goblin/Goblin4.stl', 'Green Fury Goblins', 'Green Fury Goblins/Goblin/Goblin4.png', '2018-09-11', '2018-010-15'),
    ('Green Fury Goblins/Goblin/Goblin5.stl', 'Green Fury Goblins', 'Green Fury Goblins/Goblin/Goblin5.png', '2018-09-11', '2018-010-15'),
    ('Green Fury Goblins/Goblin/Goblin6.stl', 'Green Fury Goblins', 'Green Fury Goblins/Goblin/Goblin6.png', '2018-09-11', '2018-010-15'),
    ('WhiteWolf/Goblin/Goblin.stl', 'WhiteWolf', 'WhiteWolf/Goblin/Goblin.png', '2014-04-13', '2014-04-16'),
    ('WhiteWolf/Goblin/Goblin2.stl', 'WhiteWolf', 'WhiteWolf/Goblin/Goblin2.png', '2014-04-13', '2014-04-16'),
    ('WhiteWolf/Goblin/Goblin3.stl', 'WhiteWolf', 'WhiteWolf/Goblin/Goblin3.png', '2014-04-13', '2014-04-16'),
    ('WhiteWolf/Goblin/Goblin4.stl', 'WhiteWolf', 'WhiteWolf/Goblin/Goblin4.png', '2014-04-13', '2014-04-16'),
    ('WhiteWolf/Goblin/Goblin5.stl', 'WhiteWolf', 'WhiteWolf/Goblin/Goblin5.png', '2014-04-13', '2014-04-16'),
    ('WhiteWolf/Goblin/Goblin6.stl', 'WhiteWolf', 'WhiteWolf/Goblin/Goblin6.png', '2014-04-13', '2014-04-16')

INSERT INTO Asset
VALUES
    ('D&D/Goblin/Goblin.stl', 'Goblin', 'Goblin Warrior 1', '1:1'),
    ('D&D/Goblin/Goblin2.stl', 'Goblin', 'Goblin Warrior 2', '1:1'),
    ('D&D/Goblin/Goblin3.stl', 'Goblin', 'Goblin Warrior 3', '1:1'),
    ('D&D/Goblin/Goblin4.stl', 'Goblin', 'Goblin Warrior 4', '1:1'),
    ('D&D/Goblin/Goblin5.stl', 'Goblin', 'Goblin Warrior 5', '1:1'),
    ('D&D/Goblin/Goblin6.stl', 'Goblin', 'Goblin Warrior 6', '1:1'),
    ('Bladesmith/Goblin/Goblin.stl', 'Goblin', 'Goblin Warrior 1', '1:1'),
    ('Bladesmith/Goblin/Goblin2.stl', 'Goblin', 'Goblin Warrior 2', '1:1'),
    ('Bladesmith/Goblin/Goblin3.stl', 'Goblin', 'Goblin Warrior 3', '1:1'),
    ('Bladesmith/Goblin/Goblin4.stl', 'Goblin', 'Goblin Warrior 4', '1:1'),
    ('Bladesmith/Goblin/Goblin5.stl', 'Goblin', 'Goblin Warrior 5', '1:1'),
    ('Bladesmith/Goblin/Goblin6.stl', 'Goblin', 'Goblin Warrior 6', '1:1'),
    ('Green Fury Goblins/Goblin/Goblin.stl', 'Goblin', 'Goblin Warrior 1', '1:1'),
    ('Green Fury Goblins/Goblin/Goblin2.stl', 'Goblin', 'Goblin Warrior 2', '1:1'),
    ('Green Fury Goblins/Goblin/Goblin3.stl', 'Goblin', 'Goblin Warrior 3', '1:1'),
    ('Green Fury Goblins/Goblin/Goblin4.stl', 'Goblin', 'Goblin Warrior 4', '1:1'),
    ('Green Fury Goblins/Goblin/Goblin5.stl', 'Goblin', 'Goblin Warrior 5', '1:1'),
    ('Green Fury Goblins/Goblin/Goblin6.stl', 'Goblin', 'Goblin Warrior 6', '1:1'),
    ('WhiteWolf/Goblin/Goblin.stl', 'Goblin', 'Goblin Warrior 1', '1:1'),
    ('WhiteWolf/Goblin/Goblin2.stl', 'Goblin', 'Goblin Warrior 2', '1:1'),
    ('WhiteWolf/Goblin/Goblin3.stl', 'Goblin', 'Goblin Warrior 3', '1:1'),
    ('WhiteWolf/Goblin/Goblin4.stl', 'Goblin', 'Goblin Warrior 4', '1:1'),
    ('WhiteWolf/Goblin/Goblin5.stl', 'Goblin', 'Goblin Warrior 5', '1:1'),
    ('WhiteWolf/Goblin/Goblin6.stl', 'Goblin', 'Goblin Warrior 6', '1:1')

