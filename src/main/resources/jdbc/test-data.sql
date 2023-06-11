INSERT INTO products (name, category, price) VALUES ('TV', 'Elettrodomestici', 100);
INSERT INTO products (name, category, price) VALUES ('iPhone', 'Telefonia', 250);
INSERT INTO products (name, category, price) VALUES ('Shoes', 'Abbigliamento', 50);

INSERT INTO orders (qty, total_price) VALUES (3, 250);
INSERT INTO orders (qty, total_price) VALUES (3, 750);

INSERT INTO orders_products (order_id, product_id) VALUES (1, 1);
INSERT INTO orders_products (order_id, product_id) VALUES (1, 3);
INSERT INTO orders_products (order_id, product_id) VALUES (2, 2);
