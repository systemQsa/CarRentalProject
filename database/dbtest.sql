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

INSERT INTO driver_price (price) VALUES ('10');

INSERT INTO cars (name, carClass, brand, rent_price) VALUES ('porsche', 'A', 'porsche', 10.0);
INSERT INTO cars (name,carClass,brand,rent_price) VALUES ('infinity','A','infinity',11.0);
INSERT INTO cars (name,carClass,brand,rent_price) VALUES ('toyota','B','toyota',12.0);

INSERT INTO users(name, surname, login, password, phone) VALUES ('user', 'romanov', 'user1@gmail.com', '222', '+380636789120');
INSERT INTO users(name,surname,login,password,phone) VALUES ('user2','petrov','user2@gmail.com','333','+380636789120');
INSERT INTO users(name,surname,login,password,phone) VALUES ('user3','temir','user3@gmail.com','444','+380636789120');

UPDATE users SET balance=50 WHERE login='user1@gmail.com';
UPDATE users SET balance=50 WHERE login='user3@gmail.com';
UPDATE users SET role_id = 3 WHERE id_user = 2;

INSERT INTO orders (passport, from_date, to_date, with_driver, receipt, user_id) VALUES ('AV4567','2022-04-22 17:00:00','2022-04-22 18:00:00','N',11.0,3);
INSERT INTO orders (passport, from_date, to_date, with_driver, receipt, user_id) VALUES ('AV4567','2022-04-22 17:00:00','2022-04-22 18:00:00','N',10.0,3);
INSERT INTO  orders_cars (order_id, car_id, feedback, manager_login, approved) VALUES (1,1,'feedback','user2@gmail.com','yes');
INSERT INTO  orders_cars (order_id, car_id, feedback, manager_login, approved) VALUES (2,2,'feedback2','user2@gmail.com','yes');
UPDATE users SET banned='N' WHERE login='user1@gmail.com';


 select login,o.passport,receipt,
from_date,to_date,with_driver,c.name,c.carClass,c.brand FROM users
JOIN orders o ON users.id_user = o.user_id
JOIN orders_cars oc ON o.id_order = oc.order_id
JOIN cars c ON oc.car_id = c.id_car WHERE approved='yes' order by order_id desc;

select login,balance from users;


