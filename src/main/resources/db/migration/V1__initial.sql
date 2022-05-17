CREATE SEQUENCE HIBERNATE_SEQUENCE START WITH 4 INCREMENT BY 1;

CREATE TABLE User_(
    id long primary key,
    username varchar(255) unique,
    first_name varchar(255),
    last_name varchar(225)
);


CREATE TABLE Task(
     id long primary key,
     user_id long,
     name varchar(255),
     description varchar(510),
     date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO USER_ VALUES (1, 'user_1', 'Mary', 'Jane');
INSERT INTO USER_ VALUES (2, 'user_2', 'Jon', 'Buck');
INSERT INTO USER_ VALUES (3, 'user_3', 'Jane', 'Jones');

INSERT INTO Task VALUES (1, 1, 'User1Task1', 'Testing Task1', DEFAULT );
INSERT INTO Task VALUES (2, 1, 'User1Task2', 'Testing Task2', DEFAULT);
INSERT INTO Task VALUES (3, 1, 'User1Task3', 'Testing Task3', DEFAULT);

INSERT INTO Task VALUES (4, 2, 'User2Task1', 'Testing Task1', DEFAULT);
INSERT INTO Task VALUES (5, 2, 'User2Task2', 'Testing Task2', DEFAULT);
INSERT INTO Task VALUES (6, 2, 'User2Task3', 'Testing Task3', DEFAULT);


