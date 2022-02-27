CREATE DATABASE  marvel;
USE marvel;

CREATE TABLE marvel.images (
  id int NOT NULL AUTO_INCREMENT,
  name varchar(100),
  uri varchar(200),
  type varchar(20),
  PRIMARY KEY (id)
);
CREATE TABLE marvel.characters (
  id int NOT NULL AUTO_INCREMENT,
  name varchar(50),
  description varchar(300),
  image_id int,
  PRIMARY KEY (id),
  FOREIGN KEY (image_id) REFERENCES marvel.images(id)
);
CREATE TABLE marvel.comics (
  id int NOT NULL AUTO_INCREMENT,
  title varchar(50),
  character_id int,
  PRIMARY KEY (id),
  FOREIGN KEY (character_id) REFERENCES marvel.characters(id)
);

INSERT INTO marvel.images (name, uri, type)
VALUES
	('captain america.jpg', 'http://localhost:8080/downloadFile/captain%20america.jpg', 'image/jpeg'),
	('Doctor Octopus.jpg', 'http://localhost:8080/downloadFile/Doctor%20Octopus.jpg', 'image/jpeg'),
	('doctor strange.jpg', 'http://localhost:8080/downloadFile/doctor%20strange.jpg', 'image/jpeg'),
	('hulk.jpg', 'http://localhost:8080/downloadFile/hulk.jpg', 'image/jpeg'),
	('Iron Man.jpg', 'http://localhost:8080/downloadFile/Iron%20Man.jpg', 'image/jpeg'),
	('spiderMan.jpg', 'http://localhost:8080/downloadFile/spiderMan.jpg', 'image/jpeg');


INSERT INTO marvel.characters (name, description, image_id)
VALUES
	('Doctor Strange', 'In his comic book appearances, the character is both the Hulk, a green-skinned, hulking and muscular humanoid possessing a limitless degree of physical strength, and his alter ego Dr. Robert Bruce Banner, a physically weak, socially withdrawn, and emotionally reserved physicist.', 3),
	('Captain America', 'The character wears a costume bearing an American flag motif, and he utilizes a nearly-indestructible shield that he throws as a projectile.', 1),
	('Hulk', 'In his comic book appearances, the character is both the Hulk, a green-skinned, hulking and muscular humanoid possessing a limitless degree of physical strength, and his alter ego Dr. Robert Bruce Banner, a physically weak, socially withdrawn, and emotionally reserved physicist.', 4),
	('Doctor Octopus', 'He is a highly intelligent, myopic, and somewhat stocky mad scientist who sports four strong and durable appendages resembling an octopus tentacles, which extend from the back of his body and can be used for various purposes.', 2),
	('Spider-man', 'In his origin story, he gets spider-related abilities from a bike from a radioactive spider; these include clinging to surfaces, superhuman strength and agility, and detecting danger with his spider-sense.', 6),
	('Iron Man', 'A wealthy American business magnate, playboy, philanthropist, inventor and ingenious scientist, Anthony Edward Stark. He uses the suit and successive versions to protect the world as Iron Man.', 5);
        
INSERT INTO marvel.comics (title, character_id)
VALUES
	('Doctor Strange: Master of the Mystic Arts', 1),
	('The Incredible Hulk', 3),
	('Captain America and Bucky', 2),
	('Captain America and Black Wido', 2),
	('Devils Reign: Superior Four', 4),
	('The Spectacular Spider-Man', 5),
	('Giant-Size Spider-Man', 5),
	('Iron Man and Sub-Mariner', 6),
	('Iron Man: Crash', 6),
	('Iron Man: The Legend', 6);

