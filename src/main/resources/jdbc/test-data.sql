INSERT INTO products (name, category, price) VALUES ('iPhone', 'TELEPHONY', 10.99);
INSERT INTO products (name, category, price) VALUES ('LG 2000', 'TV', 15.99);
INSERT INTO products (name, category, price) VALUES ('Folletto', 'HOUSEHOLD_APPLIANCE', 20.99);

INSERT INTO stocks (id_product, qty) VALUES (1, 5);
INSERT INTO stocks (id_product, qty) VALUES (2, 7);
INSERT INTO stocks (id_product, qty) VALUES (3, 1);

INSERT INTO regulations (law, description) VALUES (1, 'This is a law description.');
INSERT INTO regulations (law, description) VALUES (2, 'This is another law description.');

INSERT INTO orders (total_qty, total_price) VALUES (2, 21.98);
INSERT INTO orders (total_qty, total_price) VALUES (1, 15.99);

INSERT INTO orders_products (order_id, product_id) VALUES (1, 1);
INSERT INTO orders_products (order_id, product_id) VALUES (1, 2);
INSERT INTO orders_products (order_id, product_id) VALUES (2, 2);




