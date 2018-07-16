DELETE FROM meals;
DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);


INSERT INTO meals (date_time, description, calories, user_id) VALUES
  (TO_TIMESTAMP('05/30/2015 10:00:00', 'MM/DD/YYYY HH24:MI:SS'), 'Завтрак', 500, 100000),
  (TO_TIMESTAMP('05/30/2015 13:00:00', 'MM/DD/YYYY HH24:MI:SS'), 'Обед', 1000, 100000),
  (TO_TIMESTAMP('05/30/2015 20:00:00', 'MM/DD/YYYY HH24:MI:SS'), 'Ужин', 500, 100000),
  (TO_TIMESTAMP('05/31/2015 10:00:00', 'MM/DD/YYYY HH24:MI:SS'), 'Завтрак', 1000, 100000),
  (TO_TIMESTAMP('05/31/2015 13:00:00', 'MM/DD/YYYY HH24:MI:SS'), 'Обед', 500, 100000),
  (TO_TIMESTAMP('05/31/2015 20:00:00', 'MM/DD/YYYY HH24:MI:SS'), 'Ужин', 510, 100000),
  (TO_TIMESTAMP('05/31/2015 10:00:00', 'MM/DD/YYYY HH24:MI:SS'), 'Завтрак', 1020, 100001),
  (TO_TIMESTAMP('05/31/2015 12:00:00', 'MM/DD/YYYY HH24:MI:SS'), 'Обед', 480, 100001),
  (TO_TIMESTAMP('05/31/2015 21:00:00', 'MM/DD/YYYY HH24:MI:SS'), 'Ужин', 510, 100001);