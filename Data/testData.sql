
-- Inserts test data for project

INSERT INTO Attribute
VALUES
    ('Goblin', 'A little green man'),
    ('Orc', 'A large green man'),
    ('Human', 'Just a regular guy');

INSERT INTO AssetGroup
VALUES
    ('Goblin Warriors', 'A group of goblin warriors'),
    ('Orc Warriors', 'A group of orc warriors'),
    ('Human Warriors', 'A group of human warriors');

INSERT INTO AssetSet
VALUES
    ('Goblin Warriors 1', 'Goblin Warriors', 'A set of goblin warriors'),
    ('Goblin Warriors 2', 'Goblin Warriors', 'A set of goblin warriors'),
    ('Orc Warriors 1', 'Orc Warriors', 'A set of orc warriors'),
    ('Human Warriors 1', 'Human Warriors', 'A set of human warriors'),
    ('Human Warriors 2', 'Human Warriors', 'A set of human warriors');

INSERT INTO AssetRelease
VALUES
    ('Green Fury Goblins', '2015-01-02', 'Green Fury Games', 'MyMiniFactory'),
    ('Angry Orcs', '2015-01-03', 'Anthropology', 'MyMiniFactory'),
    ('Humans Settled', '2015-01-04', 'Bladesmith', 'Patreon');

INSERT INTO AssetFile
VALUES
    ('GoblinWarriors1/GoblinWarrior1.stl', 'Green Fury Goblins', 'GoblinWarriors1/GoblinWarrior1.png', '2015-01-02', '2015-01-02'),
    ('GoblinWarriors1/GoblinWarrior2.stl', 'Green Fury Goblins', 'GoblinWarriors1/GoblinWarrior2.png', '2015-01-02', '2015-01-02'),
    ('GoblinWarriors1/GoblinWarrior3.stl', 'Green Fury Goblins', 'GoblinWarriors1/GoblinWarrior3.png', '2015-01-02', '2015-01-02'),

    ('HumanWarriors1/HumanWarrior1.stl', 'Humans Settled', 'HumanWarriors1/HumanWarrior1.png', '2015-01-04', '2015-01-04'),
    ('HumanWarriors1/HumanWarrior2.stl', 'Humans Settled', 'HumanWarriors1/HumanWarrior2.png', '2015-01-04', '2015-01-04'),
    ('HumanWarriors1/HumanWarrior3.stl', 'Humans Settled', 'HumanWarriors1/HumanWarrior3.png', '2015-01-04', '2015-01-04'),

    ('HumanWarriors2/HumanWarrior1.stl', 'Humans Settled', 'HumanWarriors2/HumanWarrior1.png', '2015-01-04', '2015-01-04'),
    ('HumanWarriors2/HumanWarrior2.stl', 'Humans Settled', 'HumanWarriors2/HumanWarrior2.png', '2015-01-04', '2015-01-04'),
    ('HumanWarriors2/HumanWarrior3.stl', 'Humans Settled', 'HumanWarriors2/HumanWarrior3.png', '2015-01-04', '2015-01-04'),

    ('OrcWarriors1/OrcWarrior1.stl', 'Angry Orcs', 'OrcWarriors1/OrcWarrior1.png', '2015-01-03', '2015-01-03'),
    ('OrcWarriors1/OrcWarrior2.stl', 'Angry Orcs', 'OrcWarriors1/OrcWarrior2.png', '2015-01-03', '2015-01-03'),
    ('OrcWarriors1/OrcWarrior3.stl', 'Angry Orcs', 'OrcWarriors1/OrcWarrior3.png', '2015-01-03', '2015-01-03');

INSERT INTO Asset
VALUES
    ('GoblinWarriors1/GoblinWarrior1.stl', 'Goblin', 'Goblin Warriors 1', 'Green Fury Goblins', '1:1'),
    ('GoblinWarriors1/GoblinWarrior2.stl', 'Goblin', 'Goblin Warriors 1', 'Green Fury Goblins', '1:1'),
    ('GoblinWarriors1/GoblinWarrior3.stl', 'Goblin', 'Goblin Warriors 1', 'Green Fury Goblins', '1:1'),

    ('HumanWarriors1/HumanWarrior1.stl', 'Human', 'Human Warriors 1', 'Humans Settled', '1:1'),
    ('HumanWarriors1/HumanWarrior2.stl', 'Human', 'Human Warriors 1', 'Humans Settled', '1:1'),
    ('HumanWarriors1/HumanWarrior3.stl', 'Human', 'Human Warriors 1', 'Humans Settled', '1:1'),

    ('HumanWarriors2/HumanWarrior1.stl', 'Human', 'Human Warriors 2', 'Humans Settled', '1:1'),
    ('HumanWarriors2/HumanWarrior2.stl', 'Human', 'Human Warriors 2', 'Humans Settled', '1:1'),
    ('HumanWarriors2/HumanWarrior3.stl', 'Human', 'Human Warriors 2', 'Humans Settled', '1:1'),

    ('OrcWarriors1/OrcWarrior1.stl', 'Orc', 'Orc Warriors 1', 'Angry Orcs', '1:1'),
    ('OrcWarriors1/OrcWarrior2.stl', 'Orc', 'Orc Warriors 1', 'Angry Orcs', '1:1'),
    ('OrcWarriors1/OrcWarrior3.stl', 'Orc', 'Orc Warriors 1', 'Angry Orcs', '1:1');


INSERT INTO AppUser
VALUES
    (MD5("user1"), MD5("pass1")),
    (MD5("user2"), MD5("pass2")),
    (MD5("user3"), MD5("pass3"));

INSERT INTO Admin
VALUES
    (MD5("user2"));