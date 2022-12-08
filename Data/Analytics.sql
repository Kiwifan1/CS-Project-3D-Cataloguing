-- Develop an interesting and relevant query that involves aggregates and joins.  It should not involve any grouping.

-- This query gets the average, minimum, and maximum sizes of all files in the database downloaded from the publisher 'Bladesmith'.
--  It is interesting because it can be used for any publisher, to gain insight into the size of files provided by the publisher, as well as the relative space spent by user on keeping those files
--  It is relevant because it can be used to determine if a publisher is providing files that are too large, or if a user is keeping files that are too large, or if a user is spending too much space on these files. 


SELECT AVG(size), MIN(size), MAX(size)
FROM Asset
    JOIN Release ON (Asset.r_id = Release.id)
WHERE Release.publisher = 'Bladesmith';

-- Develop an interesting and relevant query that involves aggregates, joins, grouping and ordering.

-- This query gets the total amount of assets downloaded grouped by each publisher, and ordered in descending order
--  It is interesting because it can be used to determine which publishers are the most popular, and which are the least popular
--  It is relevant because a user could then determine their favorite publishers, and how many files they keep based upon those publishers.

SELECT Release.publisher, COUNT(*)
FROM Asset
    JOIN Release ON (Asset.r_id = Release.id)
GROUP BY Release.publisher
ORDER BY COUNT(*) DESC;

-- Develop an interesting and relevant query that involves aggregates, joins, grouping, ordering, and having.

-- This query gets the total amount of assets downloaded grouped by attributes.  It only shows attributes that have more than 1 asset downloaded.
--  It is interesting because it can be used to determine which attributes are the most popular, and which are the least popular.
--  It is relevant because a user could determine their favorite attributes, and make actions for their next print based upon what they have available.

SELECT Attribute.name, COUNT(*)
FROM Attribute
    JOIN Asset ON (Attribute.name = Asset.attribute)
GROUP BY Attribute.name
HAVING COUNT(*) > 1
ORDER BY COUNT(*) DESC;

-- Develop an interesting and relevant query that involves aggregates and subqueries.

-- This query gets the file path and scale of the largest scales in the database.
--  It is interesting because it can be used to determine the largest scale in the database.
--  It is relevant because a user could determine the largest scale they have, which generally correlates to a larger file size, and print time.

SELECT a1.filepath, a1.scale
FROM Asset a1
    JOIN (SELECT MAX(scale) max_scale
    FROM Asset) a2 ON (a1.scale = a2.max_scale);

-- Develop an interesting and relevant query that involves aggregates, grouping, having, and subqueries.

-- This query gets the file paths of the assets that have the most attributes.
--  It is interesting because it can be used to determine the most generic assets in the database.
--  It is relevant because a user could use these assets for their next setup, as it would work with a lot of different styles.

SELECT a1.filepath, COUNT(*)
FROM Asset a1
GROUP BY a1.filepath
HAVING COUNT(*) = (SELECT MAX(COUNT(*))
-- get the maximum count
FROM (SELECT COUNT(*)
    FROM Asset a2
    GROUP BY a2.filepath) a3);


-- Develop an interesting and relevant query that involves an outer join.

-- This query gets all assets that have multiple attributes, and the attributes that they have.
--  It is interesting because it can be used to determine which assets have the most attributes, and which attributes are the most popular.
-- It is relevant because a user could determine which assets they have that have the most attributes, and which attributes they have the most of.

SELECT a1.filepath, a2.name
FROM Asset a1
    LEFT JOIN Attribute a2 ON (a1.attribute = a2.name)
WHERE a1.attribute IS NOT NULL;

-- Develop an interesting and relevant query that involves the UNION set operation

-- This query gets the file paths of all assets that have the 1:10 scale, and the file paths of all assets that have the 1:20 scale.
--  It is interesting because it can be used to determine which assets are available in both scales, and which are only available in one scale.
-- It is relevant because a user could determine which assets they have that are available in both scales, and which assets they have that are only available in one scale.

    SELECT a1.filepath
    FROM Asset a1
    WHERE a1.scale = '1:10'
UNION
    SELECT a1.filepath
    FROM Asset a1
    WHERE a1.scale = '1:20';

-- Develop an interesting and relevant query that involves a CASE statement.

-- This query gets all users and labels them as 'active' if they have downloaded an asset in the last 30 days, and 'inactive' if they have not.
-- It is interesting because it can be used to determine which users are active, and which are inactive.
-- It is relevant because a user could determine which users are active, and which are inactive, and then take moderation action based upon that.

SELECT u1.username, CASE 
    WHEN u1.last_login > TIMESTAMPADD(MONTH, -1, SYSDATE()) THEN 'active' 
    ELSE 'inactive' 
    END AS 'status'
FROM AppUser u1;


-- Develop an interesting and relevant query that involves a window function.

-- This query gets all releases that have more than 1 asset, and the number of assets that they have.
--  It is interesting because it can be used to determine which releases have the most assets, and which have the least assets.
-- It is relevant because a user could determine which releases are most popular with the community, and which are least popular.

SELECT r1.id, r1.name, COUNT(*) OVER (PARTITION BY r1.id) AS 'Number of Assets'
FROM AssetRelease r1
    JOIN Asset a1 ON (r1.id = a1.rid)
GROUP BY r1.id, r1.name
HAVING COUNT(*) > 1
ORDER BY COUNT(*) DESC;

-- Develop an interesting and relevant query that involves either a WITH clause, or the creation of a view

-- This query gets the users that have the most assets added to the database.
--  It is interesting because it can be used to determine which users are using the software the most for adding assets.
-- It is relevant because a user could determine which users are using the software the most, and could ask them for feedback on the software.

WITH
    UserAssetCount
    AS
    (
        SELECT username, COUNT(*) AS 'Asset Count'
        FROM Asset
        GROUP BY username
    )
SELECT username, 'Asset Count'
FROM UserAssetCount
WHERE 'Asset Count' = (SELECT MAX('Asset Count')
FROM UserAssetCount);