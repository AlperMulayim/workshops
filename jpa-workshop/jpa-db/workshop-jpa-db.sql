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

INSERT INTO categories(category_name) VALUES("FOOD");
INSERT INTO categories(category_name) VALUES("BEVERAGES");
INSERT INTO categories(category_name) VALUES("HEALTH CARE");

INSERT INTO products(barcode,product_name,category_id) VALUES ("12345678","MYMILK MILK 1L",2);

INSERT INTO products(barcode,product_name,category_id) VALUES ("123456782","FUN MILK 1L",2);


SELECT * FROM categories;

SELECT * FROM products;