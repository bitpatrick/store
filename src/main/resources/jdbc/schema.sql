CREATE TABLE images (
    name VARCHAR(255) PRIMARY KEY,
    blob BLOB
);

CREATE TABLE products (
    id INT AUTO_INCREMENT,
    name VARCHAR(255),
    category VARCHAR(255),
    price DECIMAL(10,2),
    PRIMARY KEY(id)
);

CREATE TABLE stocks (
    id_product INT PRIMARY KEY,
    qty INT,
    FOREIGN KEY(id_product) REFERENCES products(id)
);

CREATE TABLE regulations ( 
    law INT PRIMARY KEY,
    description CLOB
);

CREATE TABLE orders (
    id INT AUTO_INCREMENT,
    username VARCHAR_IGNORECASE(50),
    total_qty INT,
    total_price DECIMAL(10,2),
    PRIMARY KEY(id),
    FOREIGN KEY(username) REFERENCES users(username)
);

CREATE TABLE balances (
    username VARCHAR_IGNORECASE(50),
	currency VARCHAR_IGNORECASE(3),
    quantity DECIMAL(10,2),
    PRIMARY KEY(username),
    FOREIGN KEY(username) REFERENCES users(username)
);

CREATE TABLE orders_products (
    order_id INT,
    product_id INT,
    FOREIGN KEY(order_id) REFERENCES orders(id),
    FOREIGN KEY(product_id) REFERENCES products(id),
    PRIMARY KEY(order_id, product_id)
);
/*
 * crea tabella che memorizza i nomi dei file , comincio da file 01 
 * 
 */
CREATE TABLE file_name(
   id INT AUTO_INCREMENT,
   name VARCHAR(500),
    PRIMARY KEY(id,name)
);
