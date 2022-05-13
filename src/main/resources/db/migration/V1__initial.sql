CREATE SEQUENCE HIBERNATE_SEQUENCE START WITH 4 INCREMENT BY 1;

CREATE TABLE User_(
    id long primary key,
    username varchar(255) unique,
    first_name varchar(255),
    last_name varchar(225)
);


CREATE TABLE Task(
     id long primary key,
     user_id long unique,
     name varchar(255),
     description varchar(510),
     date_time timestamp
);

INSERT INTO USER_ VALUES (1, 'user_1', 'Mary', 'Jane');
INSERT INTO USER_ VALUES (2, 'user_2', 'Jon', 'Buck');
INSERT INTO USER_ VALUES (3, 'user_3', 'Jane', 'Jones');

