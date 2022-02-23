DROP
ALL OBJECTS DELETE
FILES;

CREATE TABLE USER
(
    ID          BIGINT       NOT NULL PRIMARY KEY AUTO_INCREMENT,
    CREATE_DATE TIMESTAMP,
    EMAIL       VARCHAR(30)  NOT NULL,
    FIRSTNAME   VARCHAR(50)  NOT NULL,
    LASTNAME    VARCHAR(50)  NOT NULL,
    PASSWORD    VARCHAR(100) NOT NULL,
    PHONE       VARCHAR(20)  NOT NULL,
    USERNAME    VARCHAR(50)  NOT NULL
);

CREATE TABLE ROLE
(
    ID          BIGINT      NOT NULL PRIMARY KEY AUTO_INCREMENT,
    CREATE_DATE TIMESTAMP,
    NAME        VARCHAR(20) NOT NULL
);

CREATE TABLE USER_ROLE
(
    USER_ID BIGINT,
    FOREIGN KEY (USER_ID) REFERENCES USER,
    ROLE_ID BIGINT,
    FOREIGN KEY (ROLE_ID) REFERENCES ROLE
);

CREATE TABLE LIST
(
    ID          BIGINT      NOT NULL PRIMARY KEY AUTO_INCREMENT,
    CREATE_DATE DATE,
    DESCRIPTION VARCHAR(50) NOT NULL UNIQUE,
    USER_MODEL_ID BIGINT,
    FOREIGN KEY (USER_MODEL_ID) REFERENCES USER
);

CREATE TABLE TODO
(
    ID          BIGINT      NOT NULL PRIMARY KEY AUTO_INCREMENT,
    CREATE_DATE DATE,
    DESCRIPTION VARCHAR(50) NOT NULL UNIQUE,
    DEADLINE    DATE,
    LIST_ID BIGINT,
    FOREIGN KEY (LIST_ID) REFERENCES LIST
);

CREATE TABLE SUB_ITEM
(
    ID          BIGINT      NOT NULL PRIMARY KEY AUTO_INCREMENT,
    CREATE_DATE DATE,
    DESCRIPTION VARCHAR(50) NOT NULL UNIQUE,
    TODO_ID BIGINT,
    FOREIGN KEY (TODO_ID) REFERENCES TODO
);


INSERT INTO ROLE
VALUES (1,CURRENT_TIMESTAMP,'ADMIN');
INSERT INTO ROLE
VALUES (2,CURRENT_TIMESTAMP,'USER');
