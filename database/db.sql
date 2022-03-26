DROP DATABASE IF EXISTS  rental_car;
CREATE DATABASE IF NOT EXISTS rental_car;

USE rental_car;


DROP TABLE IF EXISTS feedbacks;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS cars;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS orders_cars;
DROP TABLE IF EXISTS driver_price;


CREATE TABLE roles(
    id_role INT PRIMARY KEY AUTO_INCREMENT,
    role    VARCHAR(25)
);

CREATE TABLE users(
   id_user       INT PRIMARY KEY AUTO_INCREMENT,
   name          VARCHAR(100) NOT NULL,
   surname       VARCHAR(100) NOT NULL,
   passport      VARCHAR(50),
   login         VARCHAR(255) UNIQUE,
   password      TEXT         NOT NULL,
   balance       DECIMAL(10, 2) DEFAULT 0,
   register_date DATETIME       DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   banned        CHAR(1)        DEFAULT 'N',
   phone         VARCHAR(100),
   role_id       INT            DEFAULT 2,
   FOREIGN KEY (role_id) REFERENCES roles (id_role)
);

CREATE TABLE cars(
    id_car     INT PRIMARY KEY AUTO_INCREMENT,
    name       VARCHAR(100) NOT NULL,
    carClass   VARCHAR(40)  NOT NULL,
    brand      VARCHAR(50),
    rent_price DECIMAL(10, 2),
    photo      TEXT
);

CREATE TABLE driver_price(
    id    INT PRIMARY KEY AUTO_INCREMENT,
    price DECIMAL(10, 2)
);

CREATE TABLE orders(
   id_order    INT PRIMARY KEY AUTO_INCREMENT,
   passport    VARCHAR(100) NOT NULL,
   from_date   DATETIME     NOT NULL,
   to_date     DATETIME     NOT NULL,
   with_driver VARCHAR(1),
   receipt     DECIMAL(10, 2),
   user_id     INT          NOT NULL,
   FOREIGN KEY (user_id) REFERENCES users (id_user)
);

CREATE TABLE orders_cars(
   order_id INT,
   car_id   INT,
   UNIQUE (order_id, car_id),
   FOREIGN KEY (order_id) REFERENCES orders (id_order) ON DELETE CASCADE,
   FOREIGN KEY (car_id) REFERENCES cars (id_car) ON DELETE CASCADE,
   feedback TEXT,
   manager_login VARCHAR(255),
   approved VARCHAR(12)
);

INSERT INTO roles(role) VALUES ('admin');
INSERT INTO roles(role) VALUES ('user');
INSERT INTO roles(role) VALUES ('manager');

SELECT * FROM users;
SELECT * FROM roles;
SELECT * FROM cars;
SELECT * FROM orders;
SELECT * FROM orders_cars;
SELECT * FROM driver_price;
