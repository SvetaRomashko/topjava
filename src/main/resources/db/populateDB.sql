DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;
ALTER SEQUENCE global_seq_meal RESTART WITH 10000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

insert into meals(user_id, datetime, description, calories)
VALUES (100000,'Jan-30-2021 10:00' ,'Завтрак', 500),
       (100000,'Jan-30-2021 13:00' ,'Обед', 1000),
       (100000,'Jan-30-2021 20:00' ,'Ужин', 500),
       (100000,'Jan-31-2021 0:00' ,'Еда на граничное значение',100),
       (100000,'Jan-31-2021 10:00' ,'Завтрак', 1000),
       (100000,'Jan-31-2021 13:00' ,'Обед', 500),
       (100000,'Jan-31-2021 20:00' ,'Ужин', 410),
       (100001, 'Jan-31-2021 13:00','Обед админа', 450),
       (100001, 'Jan-31-2021 10:00','Завтрак админа', 1050);
