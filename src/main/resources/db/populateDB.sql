DELETE FROM user_roles;
DELETE FROM meals;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals(description, calories, date_time, user_id) VALUES
('first meal - 1 user', 700, to_timestamp('04 Dec 2000 10:00', 'DD Mon YYYY HH24:MI'), 100000),
('second meal - 1 user', 900, to_timestamp('05 Dec 2000 14:00', 'DD Mon YYYY HH24:MI'), 100000),
('third meal - 1 user', 1100, to_timestamp('05 Dec 2000 19:00', 'DD Mon YYYY HH24:MI'), 100000),
('first meal - 2 user', 600, to_timestamp('06 Dec 2000 11:00', 'DD Mon YYYY HH24:MI'), 100001),
('second meal - 2 user', 1000, to_timestamp('06 Dec 2000 15:00', 'DD Mon YYYY HH24:MI'), 100001),
('third meal - 2 user', 300, to_timestamp('06 Dec 2000 23:00', 'DD Mon YYYY HH24:MI'), 100001);

