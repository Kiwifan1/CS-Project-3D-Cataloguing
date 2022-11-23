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
HAVING COUNT(*) = (SELECT MAX(COUNT(*)) -- get the maximum count
FROM (SELECT COUNT(*)
    FROM Asset a2
    GROUP BY a2.filepath) a3);
    