DELETE FROM user_roles;
DELETE FROM votes;
DELETE FROM dishes;
DELETE FROM restaurants;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (email, password)
VALUES ('user@yandex.ru', 'password'),
       ('admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('ROLE_USER', 100000),
       ('ROLE_ADMIN', 100001),
       ('ROLE_USER', 100001);


INSERT INTO restaurants (name)
VALUES ('Restaurant_1'),
       ('Restaurant_2'),
       ('Restaurant_3');

INSERT INTO dishes (date, name, price, restaurantId)
VALUES ('2020-01-16','Борщ', 2500, 100002),
       ('2020-01-16','Каша', 2000, 100002),
       ('2020-01-16','Котлета', 3500, 100002),
       ('2020-01-16','Суп', 2400, 100003),
       ('2020-01-16','Пюре', 1800, 100003),
       ('2020-01-16','Филе', 4000, 100003),
       ('2020-01-16','Бограч', 2800, 100004),
       ('2020-01-16','Плов', 2200, 100004),
       ('2020-01-16','Отбивная', 3800, 100004),
       ('2020-01-17','Борщ', 2600, 100002),
       ('2020-01-17','Каша', 2100, 100002),
       ('2020-01-17','Котлета', 3400, 100002);