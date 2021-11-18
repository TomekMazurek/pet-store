DROP TABLE IF EXISTS products_categories;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS users_roles;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS users;

CREATE TABLE products
(
    id             BIGINT      NOT NULL AUTO_INCREMENT,
    title          VARCHAR(50) NOT NULL,
    description    VARCHAR(50),
    stock_quantity INTEGER,
    price          DECIMAL,
    PRIMARY KEY (id)
);

CREATE TABLE categories
(
    id   BIGINT      NOT NULL AUTO_INCREMENT,
    name VARCHAR(25) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS products_categories
(
    product_id  BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    PRIMARY KEY (product_id, category_id),
    FOREIGN KEY (category_id)
        REFERENCES categories (id)
        ON DELETE CASCADE ON UPDATE NO ACTION,

    FOREIGN KEY (product_id)
        REFERENCES products (id)
        ON DELETE CASCADE ON UPDATE NO ACTION
);

CREATE TABLE IF NOT EXISTS users
(
    id       BIGINT       NOT NULL  AUTO_INCREMENT,
    name     VARCHAR(50)  NOT NULL,
    username VARCHAR(50)  NOT NULL,
    password varchar(250) NOT NULL,
    PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS roles(
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS users_roles(
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id,role_id),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE NO ACTION ,
    FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE ON UPDATE CASCADE
);

insert into products
values (1, 'collar', 'Leather collar for small dogs', 20, 20.99);
insert into products
values (2, 'collar', 'Leather collar for large dogs', 10, 25.99);
insert into categories
values (1, 'dogs');
insert into categories
values (2, 'accessories');
insert into products_categories
values (1, 1);
insert into products_categories
values (1, 2);
insert into products_categories
values (2, 1);
insert into products_categories
values (2, 2);
insert into roles values (1,'ROLE_USER');
insert into roles values (2,'ROLE_ADMIN');
