CREATE TABLE products(
    id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
    barcode VARCHAR(100),
    product_name VARCHAR(400),
    category_id INT,
    FOREIGN KEY(category_id) REFERENCES categories(id)
);

CREATE TABLE categories(
    id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
    category_name VARCHAR(400)
);


CREATE TABLE tags(
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    tag_name VARCHAR(100)
);


CREATE TABLE product_tags(
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    product_id INT NOT NULL,
    tag_id INT NOT NULL,
    FOREIGN KEY(tag_id) REFERENCES tags(id),
    FOREIGN KEY(product_id) REFERENCES products(id)
);

INSERT INTO categories(category_name) VALUES("FOOD");
INSERT INTO categories(category_name) VALUES("BEVERAGES");
INSERT INTO categories(category_name) VALUES("HEALTH CARE");

INSERT INTO products(barcode,product_name,category_id) VALUES ("12345678","MYMILK MILK 1L",2);
INSERT INTO products(barcode,product_name,category_id) VALUES ("123456782","FUN MILK 1L",2);
INSERT INTO products(barcode,product_name,category_id) VALUES ("123456782","CAKE",1);


INSERT INTO tags(tag_name) VALUES("for sale");
INSERT INTO tags(tag_name) VALUES("weekend products");

INSERT INTO tags(tag_name) VALUES("for students");

INSERT INTO product_tags(product_id,tag_id) VALUES(1,1);
INSERT INTO product_tags(product_id,tag_id) VALUES(1,2);

INSERT INTO product_tags(product_id,tag_id) VALUES(1,3);

INSERT INTO product_tags(product_id,tag_id) VALUES(3,2);
INSERT INTO product_tags(product_id,tag_id) VALUES(4,2);
INSERT INTO product_tags(product_id,tag_id) VALUES(4,3);



SELECT * FROM categories;

SELECT * FROM products;


SELECT * FROM product_tags;


SELECT  P.barcode, P.product_name, T.tag_name FROM tags AS T, product_tags as PT, products as P
WHERE T.id = PT.tag_id AND P.id = PT.product_id;