DROP TABLE IF EXISTS Form_Data;

CREATE TABLE Form_Data (
                               id INT AUTO_INCREMENT  PRIMARY KEY,
                               formName VARCHAR(250) NOT NULL,
                               email VARCHAR(250) NOT NULL,
                               numberOfChoices INT,
                               answer1 VARCHAR(250),
                               quantity1 INT,
                               answer2 VARCHAR(250),
                               quantity2 INT,
                               answer3 VARCHAR(250),
                               quantity3 INT,
                               answer4 VARCHAR(250),
                               quantity4 INT,
                               answer5 VARCHAR(250),
                               quantity5 INT
);