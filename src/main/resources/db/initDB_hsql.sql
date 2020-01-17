DROP TABLE user_roles IF EXISTS;
DROP TABLE votes IF EXISTS;
DROP TABLE dishes IF EXISTS;
DROP TABLE restaurants IF EXISTS;
DROP TABLE users IF EXISTS;
DROP SEQUENCE global_seq IF EXISTS;

CREATE SEQUENCE GLOBAL_SEQ AS INTEGER START WITH 100000;

CREATE TABLE users
(
    id       INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
    email    VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx ON USERS (email);

CREATE TABLE user_roles
(
    user_id INTEGER NOT NULL,
    role    VARCHAR(255),
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE restaurants
(
    id   INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);
CREATE UNIQUE INDEX restaurants_unique_name_idx ON restaurants (name);

CREATE TABLE dishes
(
    id           INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
    date         DATE DEFAULT now NOT NULL,
    name         VARCHAR(255)     NOT NULL,
    price        INT              NOT NULL,
    restaurantId INTEGER          NOT NULL,
    FOREIGN KEY (restaurantId) REFERENCES restaurants (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX dishes_unique_restaurant_date_name_idx ON dishes (restaurantId, date, name);

CREATE TABLE votes
(
    id            INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
    date          DATE DEFAULT now NOT NULL,
    restaurant_id INTEGER          NOT NULL,
    user_id       INTEGER          NOT NULL,
    FOREIGN KEY (restaurant_id) REFERENCES restaurants (id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX votes_unique_date_user_idx ON votes (date, user_id);