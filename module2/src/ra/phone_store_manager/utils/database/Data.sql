create database quanlydienthoai;
create schema if not exists data;
set search_path to data;

drop table if exists invoice_details;
drop table if exists invoice;
drop table if exists customer;
drop table if exists product;
drop table if exists admin;

-- 1. Tạo bảng Admin (Quản trị viên)
CREATE TABLE if not exists admin
(
    id       SERIAL PRIMARY KEY,
    username VARCHAR(50)  NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

-- 2. Tạo bảng Product (Sản phẩm)
CREATE TABLE if not exists product
(
    id    SERIAL PRIMARY KEY,
    name  VARCHAR(100)   NOT NULL,
    brand VARCHAR(50)    NOT NULL,
    price DECIMAL(12, 2) NOT NULL,
    stock INT            NOT NULL
);

-- 3. Tạo bảng Customer (Khách hàng)
CREATE TABLE if not exists customer
(
    id      SERIAL PRIMARY KEY,
    name    VARCHAR(100) NOT NULL,
    phone   VARCHAR(20),
    email   VARCHAR(100) UNIQUE,
    address VARCHAR(255)
);

-- 4. Tạo bảng Invoice (Hóa đơn)
CREATE TABLE if not exists invoice
(
    id           SERIAL PRIMARY KEY,
    customer_id  INT,
    created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(12, 2) NOT NULL,
    CONSTRAINT fk_customer FOREIGN KEY (customer_id) REFERENCES customer (id) ON DELETE SET NULL
);

-- 5. Tạo bảng Invoice_details (Chi tiết hóa đơn)
CREATE TABLE if not exists invoice_details
(
    id         SERIAL PRIMARY KEY,
    invoice_id INT,
    product_id INT,
    quantity   INT            NOT NULL,
    unit_price DECIMAL(12, 2) NOT NULL,
    CONSTRAINT fk_invoice FOREIGN KEY (invoice_id) REFERENCES invoice (id) ON DELETE CASCADE,
    CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE RESTRICT
);


update data.product
set name = ?,
    brand= ?,
    price= ?,
    stock= ?
where id=?;

select *
from product
where brand ILIKE ?;

INSERT INTO product (name, brand, price, stock)
VALUES ('iPhone 15 Pro Max 256GB', 'Apple', 34990000.00, 50),
       ('iPhone 14 128GB', 'Apple', 18990000.00, 120),
       ('iPhone 13 128GB (Chính hãng VN/A)', 'Apple', 15490000.00, 85),
       ('Samsung Galaxy S24 Ultra 512GB', 'Samsung', 37490000.00, 40),
       ('Samsung Galaxy Z Fold 5', 'Samsung', 40990000.00, 15),
       ('Samsung Galaxy A54 5G', 'Samsung', 9490000.00, 200),
       ('Xiaomi 14 Pro', 'Xiaomi', 22990000.00, 80),
       ('Xiaomi Redmi Note 13', 'Xiaomi', 5490000.00, 300),
       ('Oppo Find N3 Flip', 'Oppo', 22990000.00, 35),
       ('Oppo Reno 11 5G', 'Oppo', 10990000.00, 150),
       ('Vivo X100 Pro', 'Vivo', 25990000.00, 40),
       ('Asus ROG Phone 8 Pro', 'Asus', 28990000.00, 25);