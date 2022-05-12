CREATE SEQUENCE HIBERNATE_SEQUENCE START WITH 1 INCREMENT BY 1;

CREATE TABLE USER(
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

