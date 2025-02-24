-- create DATABASE Assignment5
use [Lab5.2]

DROP TABLE IF EXISTS CampaignParty -- model for Tc
DROP TABLE IF EXISTS Player -- model for Ta
DROP TABLE IF EXISTS PlayableCharacter -- model for Tb

    
 --Ta
CREATE TABLE Player (
	playerID int,
	experience int UNIQUE,
	characteristic int,
	PRIMARY KEY(playerID)
);

--Tb
CREATE TABLE PlayableCharacter (
	pcID int,
	pcLevel int,
	pcExperience int,
	PRIMARY KEY(pcID)
);

--Tc
CREATE TABLE CampaignParty (
	campaignID int,
	pID int,
	pcID int,
	campaignLevel int,
	PRIMARY KEY(campaignID),
	FOREIGN KEY(pID) REFERENCES Player(playerID),
	FOREIGN KEY(pcID) REFERENCES PlayableCharacter(pcID)
);

SELECT * FROM Player;
SELECT * FROM PlayableCharacter;
SELECT * FROM CampaignParty


-- Automatically populate the tables with data, the number of rows 
-- equal to the value passed as a parameter
CREATE OR ALTER PROCEDURE populateTablePlayer (@rows INT) AS
	WHILE @rows > 0
	BEGIN
		INSERT INTO Player(playerID, experience, characteristic) VALUES
		(@rows, @rows * 25, (@rows) * 5 % 17)
		SET @rows = @rows - 1
	END
GO

CREATE OR ALTER PROCEDURE populateTablePlayableCharacter (@rows INT) AS
    WHILE @rows > 0
    BEGIN
        INSERT INTO PlayableCharacter(pcID, pcLevel, pcExperience) VALUES
        (@rows, @rows * 17, (@rows) * 3 % 25)
        SET @rows = @rows - 1
    END
GO

CREATE OR ALTER PROCEDURE populateTableCampaignParty (@rows INT) AS
    DELETE FROM CampaignParty
    WHILE @rows > 0
    BEGIN
        INSERT INTO CampaignParty(campaignID, pID, pcID, campaignLevel) VALUES
        (@rows, @rows, @rows, (@rows ) * 13 % 17)
        SET @rows = @rows - 1
    END
GO

-- Populating all tables with 1000 rows
EXEC populateTablePlayer 1000
EXEC populateTablePlayableCharacter 1000
EXEC populateTableCampaignParty 1000

delete from Player
delete from PlayableCharacter
delete from CampaignParty

-- Clustered Index Scan
SELECT playerID as p_target_id
    FROM Player
ORDER BY playerID DESC

-- Clustered Index Seek
SELECT playerID
    FROM Player
WHERE playerID > 0 AND playerID < 70

-- Non-clustered Index Scan 
SELECT playerID as player_target_id
FROM Player

-- Non-clustered Index Seek
SELECT experience
    FROM Player
WHERE experience > 20 and experience < 50

-- Key Lookup (Non-clustered)
SELECT experience 
	FROM Player
WHERE experience = 100 and characteristic > 20



-- b. Write a query on table Tb with a WHERE clause 
-- of the form WHERE b2 = value and analyze its execution 
-- plan. Create a nonclustered index that can speed up the query. 
-- Examine the execution plan again.


SELECT *
FROM PlayableCharacter
WHERE pclevel = 51

CREATE NONCLUSTERED INDEX index_1 ON PlayableCharacter(pcLevel)
DROP INDEX index_1 on PlayableCharacter

-- c. Create a view that joins at least 2 tables. 
-- Check whether existing indexes are helpful; if not, 
-- reassess existing indexes / examine the cardinality of the tables.


CREATE OR ALTER VIEW tablesView AS
	SELECT Player.playerID FROM Player
    JOIN PlayableCharacter
        ON Player.playerID = PlayableCharacter.pcID 
    JOIN CampaignParty
        on CampaignParty.pID = Player.playerID
	WHERE Player.experience > 50
GO

SELECT * FROM tablesView

CREATE NONCLUSTERED INDEX index_1 ON PlayableCharacter(pcLevel)
DROP INDEX index_1 on PlayableCharacter

CREATE NONCLUSTERED INDEX index_2 ON Player(experience)
DROP INDEX index_2 on Player

exec sp_helpindex Player
exec sp_helpindex PlayableCharacter
exec sp_helpindex CampaignParty