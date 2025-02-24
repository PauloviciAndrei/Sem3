CREATE TABLE Publishers (
    PublisherID INT IDENTITY PRIMARY KEY,
    PublisherName NVARCHAR(255) NOT NULL
);

CREATE TABLE Games (
    GameID INT IDENTITY PRIMARY KEY,
    GameTitle NVARCHAR(255) NOT NULL,
    PublisherID INT,
    ReleaseDate DATE,
    FOREIGN KEY (PublisherID) REFERENCES Publishers(PublisherID)
);

CREATE TABLE Platforms (
    PlatformID INT IDENTITY PRIMARY KEY,
    PlatformName NVARCHAR(100) NOT NULL
);

CREATE TABLE Genres (
    GenreID INT IDENTITY PRIMARY KEY,
    GenreName NVARCHAR(100) NOT NULL
);

CREATE TABLE Developers (
    DeveloperID INT IDENTITY PRIMARY KEY,
    DeveloperName NVARCHAR(255) NOT NULL,
    Country NVARCHAR(100)
);

CREATE TABLE Users (
    UserID INT IDENTITY PRIMARY KEY,
    UserName NVARCHAR(100) NOT NULL,
    Email NVARCHAR(255) NOT NULL UNIQUE,
    RegisteredDate DATE NOT NULL
);

CREATE TABLE Ratings (
    RatingID INT IDENTITY PRIMARY KEY,
    GameID INT,
    UserID INT,
    Rating INT CHECK (Rating BETWEEN 1 AND 10),
    RatingDate DATE,
    FOREIGN KEY (GameID) REFERENCES Games(GameID),
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
);

CREATE TABLE GamePlatforms (
    GameID INT,
    PlatformID INT,
    PRIMARY KEY (GameID, PlatformID),
    FOREIGN KEY (GameID) REFERENCES Games(GameID),
    FOREIGN KEY (PlatformID) REFERENCES Platforms(PlatformID)
);

CREATE TABLE GameGenres (
    GameID INT,
    GenreID INT,
    PRIMARY KEY (GameID, GenreID),
    FOREIGN KEY (GameID) REFERENCES Games(GameID),
    FOREIGN KEY (GenreID) REFERENCES Genres(GenreID)
);

CREATE TABLE GameDevelopers (
    GameID INT,
    DeveloperID INT,
    PRIMARY KEY (GameID, DeveloperID),
    FOREIGN KEY (GameID) REFERENCES Games(GameID),
    FOREIGN KEY (DeveloperID) REFERENCES Developers(DeveloperID)
);

INSERT INTO Publishers (PublisherName) VALUES 
('Nintendo'), 
('Electronic Arts'), 
('Activision Blizzard'), 
('Ubisoft'), 
('Sony Interactive Entertainment'), 
('Square Enix'), 
('Bethesda Softworks'), 
('Valve Corporation'), 
('Epic Games'), 
('CD Projekt');

-- Insert data into Games
INSERT INTO Games (GameTitle, PublisherID, ReleaseDate) VALUES 
('The Legend of Zelda: Breath of the Wild', 1, '2017-03-03'), 
('FIFA 22', 2, '2021-10-01'), 
('Epic Quest', 1, '2023-07-15'),
('Call of Duty: Modern Warfare', 3, '2019-10-25'), 
('Assassin''s Creed Odyssey', 4, '2018-10-05'), 
('God of War', 5, '2018-04-20'), 
('Final Fantasy XV', 6, '2016-11-29'), 
('The Elder Scrolls V: Skyrim', 7, '2011-11-11'), 
('Half-Life 2', 8, '2004-11-16'), 
('Fortnite', 9, '2017-07-21'), 
('Cyberpunk 2077', 10, '2020-12-10'),
('Epic Quest', 1 , '2023-07-15');

INSERT INTO Games (GameTitle, PublisherID, ReleaseDate)
VALUES
('Space Adventures', 2, '2023-07-22'),
('Battle of Legends', 3, '2023-08-30');

INSERT INTO Games (GameTitle, PublisherID, ReleaseDate)
VALUES
('Assassis Creed Origins', 4 , '2023-07-22')

-- Insert data into Platforms
INSERT INTO Platforms (PlatformName) VALUES 
('PlayStation 4'), 
('Xbox One'), 
('Nintendo Switch'), 
('PC'), 
('PlayStation 5'), 
('Xbox Series X'), 
('Mobile'), 
('Stadia'), 
('Nintendo Wii'), 
('VR');

-- Insert data into Genres
INSERT INTO Genres (GenreName) VALUES 
('Action'), 
('Adventure'), 
('RPG'), 
('Shooter'), 
('Puzzle'), 
('Racing'), 
('Simulation'), 
('Sports'), 
('Horror'),
(1),
('Strategy');

-- Insert data into Developers
INSERT INTO Developers (DeveloperName, Country) VALUES 
('Nintendo EPD', 'Japan'), 
('Electronic Arts Vancouver', 'Canada'), 
('Infinity Ward', 'USA'), 
('Ubisoft Quebec', 'Canada'), 
('Santa Monica Studio', 'USA'), 
('Square Enix', 'Japan'), 
('Bethesda Game Studios', 'USA'), 
('Valve Corporation', 'USA'), 
('Epic Games', 'USA'), 
('CD Projekt Red', 'Poland');

-- Insert data into Users
INSERT INTO Users (UserName, Email, RegisteredDate) VALUES 
('GamerA', 'gamera@example.com', '2020-08-15'), 
('PlayerOne', 'playerone@example.com', '2019-05-25'), 
('AceGamer', 'acegamer@example.com', '2021-03-10'), 
('CoolCat', 'coolcat@example.com', '2022-01-01'), 
('GameFan', 'gamefan@example.com', '2020-06-19'), 
('ProGamer123', 'progamer123@example.com', '2019-11-23'), 
('LevelUp', 'levelup@example.com', '2022-09-15'), 
('HighScore', 'highscore@example.com', '2021-12-05'), 
('Champion', 'champion@example.com', '2020-02-28'), 
('NoobMaster69', 'noobmaster69@example.com', '2021-04-17');

INSERT INTO Users (UserName, Email, RegisteredDate)
VALUES
('Alice', 'alice@example.com', '2023-02-01'),
('Bob', 'bob@example.com', '2023-03-05'),
('Charlie', 'charlie@example.com', '2023-04-10'),
('David', 'david@example.com', '2023-05-15');

INSERT INTO Ratings (GameID, UserID, Rating, RatingDate) VALUES 
(1, 1, 10, '2020-09-01'), 
(2, 2, 8, '2021-10-03'), 
(3, 3, 7, '2019-11-10'), 
(4, 4, 9, '2020-01-10'), 
(5, 5, 10, '2018-05-02'), 
(6, 6, 6, '2017-12-12'), 
(7, 7, 8, '2019-11-30'), 
(8, 8, 10, '2005-01-03'), 
(9, 9, 5, '2020-07-15'), 
(10, 10, 7, '2021-12-21');

INSERT INTO Ratings (GameID, UserID, Rating, RatingDate)
VALUES
(1, 1, 8, '2023-07-16'),
(1, 2, 9, '2023-07-17'),
(1, 3, 7, '2023-07-18'),
(1, 4, 8, '2023-07-19'),
(1, 5, 6, '2023-07-20');

INSERT INTO Ratings (GameID, UserID, Rating, RatingDate)
VALUES
(2, 1, 8, '2023-07-23'),  
(2, 2, 7, '2023-07-24'),  
(2, 3, 9, '2023-07-25'),  
(2, 4, 6, '2023-07-26');  

INSERT INTO Ratings (GameID, UserID, Rating, RatingDate)
VALUES
(3, 1, 8, '2023-08-31'),  
(3, 2, 7, '2023-09-01'),  
(3, 3, 9, '2023-09-02'),  
(3, 4, 6, '2023-09-03');  


INSERT INTO GamePlatforms (GameID, PlatformID) VALUES 
(1, 3), 
(2, 1), 
(3, 2), 
(4, 1), 
(5, 1), 
(6, 4), 
(7, 5), 
(8, 4), 
(9, 7), 
(10, 4);



INSERT INTO GameGenres (GameID, GenreID) VALUES 
(1, 2), 
(2, 8), 
(3, 4), 
(4, 2), 
(5, 1), 
(6, 3), 
(7, 3), 
(8, 1), 
(9, 1), 
(10, 3);

INSERT INTO GameDevelopers (GameID, DeveloperID) VALUES 
(1, 1), 
(2, 2), 
(3, 3), 
(4, 4), 
(5, 5), 
(6, 6), 
(7, 7), 
(8, 8), 
(9, 9), 
(10, 10);

UPDATE Games
SET ReleaseDate = DATEADD(YEAR, 1, ReleaseDate)
WHERE PublisherID = (SELECT PublisherID FROM Publishers WHERE PublisherName = 'Electronic Arts')
    AND ReleaseDate >= '2020-01-01';

UPDATE Users
SET UserName = CONCAT(UserName, '_VIP')
WHERE RegisteredDate BETWEEN '2021-01-01' AND '2022-12-31'
    AND Email LIKE '%gamer%';

UPDATE Ratings
SET Rating = Rating + 1
WHERE Rating < 8
    AND UserID IN (SELECT UserID FROM Users WHERE RegisteredDate < '2021-01-01' AND UserName NOT LIKE '%Noob%');

DELETE FROM GameGenres
WHERE GenreID IN (SELECT GenreID FROM Genres WHERE GenreName IN ('Puzzle', 'Horror'))
    AND GameID IN (SELECT GameID FROM Games WHERE ReleaseDate < '2010-01-01');

DELETE FROM Users
WHERE Email IS NULL
    OR (RegisteredDate > '2022-01-01' AND UserName LIKE '%Test%');

	/* A */
	/* Find all game titles that are published by "Nintendo" or developed by "Nintendo EPD". */
SELECT GameTitle
FROM Games
WHERE PublisherID = (SELECT PublisherID FROM Publishers WHERE PublisherName = 'Nintendo')
UNION
SELECT GameTitle
FROM Games
WHERE GameID IN (SELECT GameID FROM GameDevelopers WHERE DeveloperID = (SELECT DeveloperID FROM Developers WHERE DeveloperName = 'Nintendo EPD'));

/*Find all users who registered before 2021 or have an email ending with "gmail.com", and include all matches even if there are duplicates.*/
SELECT UserName
FROM Users
WHERE RegisteredDate < '2021-01-01'
UNION ALL
SELECT UserName
FROM Users
WHERE Email LIKE '%@gmail.com';

	/* B */
	/* This query retrieves users who have rated a game and registered in the year 2023.*/
SELECT UserID
FROM Ratings
WHERE RatingDate BETWEEN '2023-01-01' AND '2023-12-31'
INTERSECT
SELECT UserID
FROM Users
WHERE RegisteredDate BETWEEN '2023-01-01' AND '2023-12-31';


/*Find all users who have rated games and registered in 2021. */

SELECT UserID
FROM Users
WHERE RegisteredDate BETWEEN '2021-01-01' AND '2021-12-31'
INTERSECT
SELECT UserID
FROM Ratings
WHERE UserID IN (SELECT UserID FROM Users);

/* C */

/* Find game titles published by "Ubisoft" but not rated by any user.*/

SELECT GameTitle
FROM Games
WHERE PublisherID = (SELECT PublisherID FROM Publishers WHERE PublisherName = 'Ubisoft')
EXCEPT
SELECT GameTitle
FROM Games
WHERE GameID IN (SELECT GameID FROM Ratings);

/*Find all platforms that are not associated with any game. */

SELECT PlatformName
FROM Platforms
WHERE PlatformID NOT IN (SELECT PlatformID FROM GamePlatforms)
ORDER BY PlatformName;


	/* D */

/* This query retrieves the titles of games along with their publishers and developers */
SELECT g.GameTitle, p.PublisherName, d.DeveloperName
FROM Games g
INNER JOIN Publishers p ON g.PublisherID = p.PublisherID
INNER JOIN GameDevelopers gd ON g.GameID = gd.GameID
INNER JOIN Developers d ON gd.DeveloperID = d.DeveloperID;

/* This query retrieves all users and any ratings they have given */

SELECT u.UserName, r.Rating
FROM Users u
LEFT JOIN Ratings r ON u.UserID = r.UserID
ORDER BY Rating;

/* This query retrieves all games and their associated platforms */

SELECT g.GameTitle, p.PlatformName
FROM Games g
RIGHT JOIN GamePlatforms gp ON g.GameID = gp.GameID
RIGHT JOIN Platforms p ON gp.PlatformID = p.PlatformID;

/* This query retrieves all users and all ratings */

SELECT u.UserName, r.Rating
FROM Users u
FULL JOIN Ratings r ON u.UserID = r.UserID
ORDER BY Rating;

/* E */
/* This query finds all users who have rated games that were released in 2023 and have a rating of 8 or higher.
The subquery inside the IN clause filters based on the game’s ReleaseDate and rating.*/
SELECT UserName
FROM Users
WHERE UserID IN (
    SELECT r.UserID
    FROM Ratings r
    WHERE r.GameID IN (
        SELECT g.GameID
        FROM Games g
        WHERE g.ReleaseDate BETWEEN '2023-01-01' AND '2023-12-31'
    )
    AND r.Rating >= 8
);

	/* F */

/* This query finds top 5 users who have rated at least one game. */

SELECT TOP 5 UserName
FROM Users u
WHERE EXISTS (
    SELECT 1
    FROM Ratings r
    WHERE r.UserID = u.UserID
);


/* This query finds all games that have been rated by users who registered in 2023 */

SELECT GameTitle
FROM Games g
WHERE EXISTS (
    SELECT 1
    FROM Ratings r
    WHERE r.GameID = g.GameID
    AND EXISTS (
        SELECT 1
        FROM Users u
        WHERE u.UserID = r.UserID
        AND u.RegisteredDate BETWEEN '2023-01-01' AND '2023-12-31'
    )
);

	/* G */

--This query finds the average rating of all games that have received at least 5 ratings.
--The subquery calculates the total ratings and the count of ratings for each game, and then the outer query computes the average rating.

SELECT GameID, AVG(Rating) AS AvgRating
FROM Ratings
WHERE GameID IN (
    SELECT GameID
    FROM Ratings
    GROUP BY GameID
    HAVING COUNT(Rating) >= 5
)
GROUP BY GameID;

--This query finds the average GameID for each PublisherID based on the number of games they have published.

SELECT PublisherID, AVG(GameID) AS AvgGameID
FROM (
    SELECT DISTINCT PublisherID, GameID
    FROM Games
    WHERE ReleaseDate BETWEEN '2023-01-01' AND '2023-12-31'
) AS GameData
GROUP BY PublisherID;

	-- H --

--This query finds the total ratings for each game but only shows games with a total rating sum greater than 30.

SELECT GameID, SUM(Rating) AS TotalRating
FROM Ratings
GROUP BY GameID
HAVING SUM(Rating) > 3*10;

--This query finds the games where the minimum rating is greater than the minimum rating for all other games.

SELECT GameID, MIN(Rating) AS MinRating
FROM Ratings
GROUP BY GameID
HAVING MIN(Rating) > (
    SELECT MIN(Rating)
    FROM Ratings
);

--This query finds the games that were rated the highest by any user and only includes games where the maximum rating is greater than or equal to 9.

SELECT GameID, MAX(Rating) AS MaxRating
FROM Ratings
GROUP BY GameID
HAVING MAX(Rating) >= 3 * 3;

	-- I --

--This query finds games where the rating is greater than any of the ratings given by UserID = 1

SELECT GameTitle, Rating
FROM Ratings r
JOIN Games g ON r.GameID = g.GameID
WHERE r.Rating > ANY (
    SELECT Rating
    FROM Ratings
    WHERE UserID = 1
);



--This query finds games whose release date is later than any release date of games from the publisher with PublisherID = 3

SELECT GameTitle, ReleaseDate
FROM Games g
WHERE g.ReleaseDate > ANY (
    SELECT ReleaseDate
    FROM Games
    WHERE PublisherID = 3
);


-- This query finds games where the rating is greater than or equal to all the ratings for that specific game.

SELECT GameTitle, Rating
FROM Ratings r
JOIN Games g ON r.GameID = g.GameID
WHERE r.Rating >= ALL (
    SELECT Rating
    FROM Ratings
    WHERE GameID = g.GameID
);



--This query finds publishers whose PublisherID is associated with all games that have more than 5 titles.

SELECT PublisherID, PublisherName
FROM Publishers p
WHERE p.PublisherID = ALL (
    SELECT PublisherID
    FROM Games
    GROUP BY PublisherID
    HAVING COUNT(GameID) > 5
);



DELETE  FROM Games;
SELECT * FROM Games;
SELECT * FROM Users;
SELECT * FROM Ratings;
SELECT * FROM GameGenres;
SELECT * FROM Users;
SELECT * FROM Genres;
SELECT * FROM GameDevelopers;

