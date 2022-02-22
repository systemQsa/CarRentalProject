DROP DATABASE IF EXISTS  rental_car;
CREATE DATABASE IF NOT EXISTS rental_car;

USE rental_car;


ALTER TABLE users ADD COLUMN salt TEXT;


CREATE TABLE users(
                      id_user INT PRIMARY KEY AUTO_INCREMENT,
                      name VARCHAR(100) NOT NULL ,
                      surname VARCHAR(100) NOT NULL ,
                      passport VARCHAR(50),
                      login VARCHAR(255) UNIQUE ,
                      password TEXT NOT NULL ,
                      balance DECIMAL(10,2) DEFAULT 0,
                      register_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                      banned CHAR(1) DEFAULT 'N',
                      phone VARCHAR(100),
                      role_id INT DEFAULT 2,
                      FOREIGN KEY (role_id) REFERENCES roles(id_role)
);

CREATE TABLE roles(
                      id_role INT PRIMARY KEY AUTO_INCREMENT,
                      role VARCHAR(25)
);

CREATE TABLE cars(
                     id_car INT PRIMARY KEY AUTO_INCREMENT,
                     name VARCHAR(100) NOT NULL ,
                     carClass VARCHAR(40) NOT NULL ,
                     brand VARCHAR(50),
                     rent_price DECIMAL(10,2),
                     photo TEXT
);

CREATE TABLE driver_price(
                             id INT PRIMARY KEY AUTO_INCREMENT,
                             price DECIMAL(10,2)
);

CREATE TABLE orders(
                       id_order INT PRIMARY KEY AUTO_INCREMENT,
                       passport VARCHAR(100),
                       from_date DATETIME NOT NULL ,
                       to_date DATETIME NOT NULL ,
                       with_driver VARCHAR(1),
                       receipt DECIMAL(10,2),
                       user_id INT NOT NULL ,
                       FOREIGN KEY (user_id) REFERENCES users(id_user)
);

CREATE TABLE orders_cars(
                            order_id INT ,
                            car_id INT ,
                            UNIQUE (order_id,car_id),
                            FOREIGN KEY (order_id) REFERENCES orders(id_order) ON DELETE CASCADE ,
                            FOREIGN KEY (car_id) REFERENCES cars(id_car) ON DELETE CASCADE
);



DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS cars;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS orders_cars;
DROP TABLE driver_price;

TRUNCATE TABLE orders;
TRUNCATE TABLE orders_cars;


SELECT * FROM users;
SELECT * FROM roles;
SELECT * FROM cars;
SELECT * FROM orders;
SELECT * FROM orders_cars;
SELECT * FROM driver_price;


ALTER TABLE users ADD COLUMN phone VARCHAR(30);
ALTER TABLE users MODIFY  COLUMN banned  VARCHAR(1);
ALTER TABLE users MODIFY  COLUMN banned  CHAR(1) DEFAULT 'N';
ALTER TABLE users MODIFY  COLUMN banned  CHAR(1);
ALTER TABLE  cars RENAME COLUMN carClass TO carClass;
#ALTER TABLE users ALTER COLUMN register_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

UPDATE users SET role_id=1 WHERE id_user=1;
UPDATE cars SET name='AJAX', carClass='V',brand='PORSHE',rent_price=1200.9 WHERE id_car=8;
UPDATE users SET balance=1200.2 WHERE login='sa@gmail.com';
UPDATE users SET banned='Y' WHERE login='user@gmail.com';
UPDATE users SET banned='N' WHERE login='user2@gmail.com';

update users  set register_date= '2023-02-14 23:45:38' where id_user=1;

update orders set from_date='2023-02-14' where id_order=1;

INSERT INTO users(name,surname,login,password,phone) values ('newUser','romanov','user2@gmail.com','222','+380636789120');
INSERT INTO users(name,surname,login,password,phone) values ('user3','moma','user3@gmail.com','222','+380636789120');
INSERT INTO roles (role) VALUES ('admin');
INSERT INTO roles (role) VALUES ('user');
INSERT INTO roles (role) VALUES ('manager');

INSERT INTO driver_price (price) VALUES (100);

DELETE FROM users where id_user=1;
DELETE FROM users where id_user=16;
DELETE FROM users where id_user=11;
SELECT id_user,name,surname,login,phone,banned,register_date FROM users ORDER BY id_user DESC;

INSERT INTO cars (name,carClass,brand,rent_price) VALUES ('PORSHE','A','PORSHE',980.99);

select DISTINCT(name) from cars where carClass='A';
select name,carClass,count(id_car) from cars group by name, carClass;
SELECT name,carClass,brand,rent_price,photo FROM cars WHERE id_car=8;