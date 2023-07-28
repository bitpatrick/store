INSERT INTO users (username, password, enabled,token_qty,currency) VALUES ('supermario', '{bcrypt}$2a$12$jAYTU4q6a7Os45R9oOlknuXloS0ElcOLHvyCvlDXUERCwvEl2JA8O', true,1.5,'USD');
INSERT INTO authorities (username, authority) VALUES ('supermario', 'ROLE_USER'); 

INSERT INTO products (name, category, price) VALUES ('iphone', 'TELEPHONY', 10.99);
INSERT INTO products (name, category, price) VALUES ('lg-tv', 'TV', 15.99);
INSERT INTO products (name, category, price) VALUES ('folletto', 'HOUSEHOLD_APPLIANCE', 20.99);
INSERT INTO products (name, category, price) VALUES ('samsung-s9', 'TELEPHONY', 39.99);

INSERT INTO stocks (id_product, qty) VALUES (1, 5);
INSERT INTO stocks (id_product, qty) VALUES (2, 7);
INSERT INTO stocks (id_product, qty) VALUES (3, 1);

INSERT INTO regulations (law, description) VALUES (1, 'This is a law description.');
INSERT INTO regulations (law, description) VALUES (2, 'This is another law description.');

INSERT INTO orders (username, total_qty, total_price) VALUES ('supermario', 2, 21.98);
INSERT INTO orders (username, total_qty, total_price) VALUES ('supermario', 1, 15.99);

INSERT INTO orders_products (order_id, product_id) VALUES (1, 1);
INSERT INTO orders_products (order_id, product_id) VALUES (1, 2);
INSERT INTO orders_products (order_id, product_id) VALUES (2, 2);




