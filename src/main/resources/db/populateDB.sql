DELETE FROM user_roles;
DELETE FROM votes;
DELETE FROM dishes;
DELETE FROM restaurants;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (email, password)
VALUES ('user@gmail.com', 'password'),
       ('admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('ROLE_USER', 100000),
       ('ROLE_ADMIN', 100001),
       ('ROLE_USER', 100001);

INSERT INTO restaurants (name)
VALUES ('Restaurant_1'),
       ('Restaurant_2'),
       ('Restaurant_3');

INSERT INTO dishes (date, name, price, restaurant_id)
VALUES ('2020-01-10', 'Борщ', 2500, 100002),
       ('2020-01-10', 'Каша', 2000, 100002),
       ('2020-01-10', 'Котлета', 3500, 100003),
       ('2020-01-10', 'Суп', 2400, 100003),
       ('2020-01-10', 'Пюре', 1800, 100004),
       ('2020-01-10', 'Филе', 4000, 100004),
       ('2020-01-11', 'Бограч', 2800, 100002),
       ('2020-01-11', 'Плов', 2200, 100002),
       ('2020-01-11', 'Отбивная', 3800, 100003),
       ('2020-01-11', 'Борщ', 2600, 100003),
       ('2020-01-11', 'Каша', 2100, 100004),
       ('2020-01-11', 'Котлета', 3400, 100004);

INSERT INTO dishes (name, price, restaurant_id)
VALUES ('Борщ', 2500, 100002),
       ('Каша', 2000, 100002),
       ('Котлета', 3500, 100002),
       ('Суп', 2400, 100003),
       ('Пюре', 1800, 100003),
       ('Филе', 4000, 100003),
       ('Бограч', 2800, 100004),
       ('Плов', 2200, 100004),
       ('Отбивная', 3800, 100004);

INSERT INTO votes (date, user_id, restaurant_id)
VALUES ('2020-01-10', 100000, 100002),
       ('2020-01-10', 100001, 100002),
       ('2020-01-11', 100000, 100003),
       ('2020-01-11', 100001, 100004);

INSERT INTO votes (user_id, restaurant_id)
VALUES (100001, 100002);