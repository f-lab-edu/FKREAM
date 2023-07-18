SET FOREIGN_KEY_CHECKS = 0;

drop table if exists users;
drop table if exists address;
drop table if exists brand;
drop table if exists deal;
drop table if exists interested_item;
drop table if exists item;
drop table if exists item_category;
drop table if exists item_img;
drop table if exists item_size_price;
drop table if exists manager;
drop table if exists notification;
drop table if exists owned_items;
drop table if exists payment_card;
drop table if exists sales_account;

CREATE TABLE users
(
    id                      int PRIMARY KEY AUTO_INCREMENT,
    email                   varchar(255) UNIQUE,
    password                varchar(255),
    fourteen_agreement      boolean,
    gender                  varchar(255),
    ad_agreement            boolean,
    personal_authentication boolean,
    phone_number            varchar(255),
    profile_name            varchar(255),
    name                    varchar(255),
    profile_img_name        varchar(255),
    profile_img_url         varchar(255),
    profile_img_origin_name varchar(255),
    created_at              timestamp,
    modified_at             timestamp
)Engine=InnoDB CHARACTER SET utf8mb4;
create index idx_email on users (email);

CREATE TABLE manager
(
    id              int PRIMARY KEY AUTO_INCREMENT,
    employee_number varchar(255),
    password        varchar(255),
    name            varchar(255),
    phone_number    varchar(255),
    agreement       boolean,
    created_at      timestamp,
    modified_at     timestamp
)Engine=InnoDB CHARACTER SET utf8mb4;
create index idx_employee_number on manager (employee_number);

CREATE TABLE address
(
    id              int PRIMARY KEY,
    user_id         int,
    name            varchar(255),
    phone_number    varchar(255),
    zipcode         varchar(255),
    detail1         varchar(255),
    detail2         varchar(255),
    default_address boolean,
    created_at      timestamp
)Engine=InnoDB CHARACTER SET utf8mb4;

CREATE TABLE payment_card
(
    id           int PRIMARY KEY AUTO_INCREMENT,
    user_id      int,
    card_company varchar(255),
    card_number  varchar(255),
    expiration   varchar(255),
    card_pw      varchar(255),
    created_at   timestamp
)Engine=InnoDB CHARACTER SET utf8mb4;

CREATE TABLE sales_account
(
    id             int PRIMARY KEY AUTO_INCREMENT,
    user_id        int,
    bank_name      varchar(255),
    account_number varchar(255),
    account_holder varchar(255),
    created_at     timestamp
)Engine=InnoDB CHARACTER SET utf8mb4;

CREATE TABLE notification
(
    id                 int PRIMARY KEY AUTO_INCREMENT,
    user_id            int,
    interested_item_id int,
    type               varchar(255),
    created_at         timestamp
)Engine=InnoDB CHARACTER SET utf8mb4;

CREATE TABLE item
(
    id                   int PRIMARY KEY AUTO_INCREMENT,
    item_name            varchar(255),
    model_number         varchar(255),
    category_id          int,
    detailed_category_id int,
    gender               varchar(255),
    release_date         timestamp,
    representative_color varchar(255),
    released_price       int,
    latest_price         int,
    brand_id             int,
    created_at           timestamp,
    modified_at          timestamp,
    manager_id           int
)Engine=InnoDB CHARACTER SET utf8mb4;
CREATE
FULLTEXT INDEX item_name_idx ON ITEM (item_name) WITH PARSER ngram;
Create
FULLTEXT index model_number_idx on ITEM (model_number) WITH PARSER ngram;
CREATE INDEX idx_item_covering
    ON ITEM (item_name, BRAND_ID);

CREATE TABLE item_category
(
    id                 int PRIMARY KEY AUTO_INCREMENT,
    category_name      varchar(255) unique,
    parent_category_id int,
    foreign key (`parent_category_id`) references ITEM_CATEGORY (id) on delete set null
)Engine=InnoDB CHARACTER SET utf8mb4;
create index idx_category_name on item_category (category_name);

CREATE TABLE item_size_price
(
    id                     int PRIMARY KEY AUTO_INCREMENT,
    item_id                int,
    size                   varchar(255),
    lowest_selling_price   int,
    highest_purchase_price int,
    modified_at            timestamp
)Engine=InnoDB CHARACTER SET utf8mb4;
create index idx_item_id on item_size_price (item_id);
create index idx_lowest_selling_price on item_size_price (lowest_selling_price);
CREATE INDEX idx_item_size_price_covering
    ON ITEM_SIZE_PRICE (ITEM_ID, LOWEST_SELLING_PRICE);

CREATE TABLE item_img
(
    id                    int PRIMARY KEY AUTO_INCREMENT,
    item_id               int,
    img_name              varchar(255),
    img_url               varchar(255),
    origin_name           varchar(255),
    is_representative_img boolean,
    created_at            timestamp
)Engine=InnoDB CHARACTER SET utf8mb4;
CREATE INDEX idx_item_img
    ON item_img (ITEM_ID);


CREATE TABLE deal
(
    id                 int PRIMARY KEY AUTO_INCREMENT,
    item_id            int,
    deal_type          varchar(255),
    user_id            int,
    price              int,
    size               varchar(255),
    `period`           timestamp,
    utilization_policy boolean,
    sales_condition    boolean,
    dealStatus             varchar(255),
    other_id           int,
    created_at         timestamp,
    trading_day        timestamp
)Engine=InnoDB CHARACTER SET utf8mb4;
create index idx_item_id on deal (item_id);

CREATE TABLE interested_item
(
    id                 int PRIMARY KEY AUTO_INCREMENT,
    user_id            int,
    item_size_price_id int
)Engine=InnoDB CHARACTER SET utf8mb4;

CREATE TABLE brand
(
    id         int PRIMARY KEY AUTO_INCREMENT,
    brand_name varchar(255),
    is_luxury  boolean
)Engine=InnoDB CHARACTER SET utf8mb4;
Create FULLTEXT index brand_name_idx on BRAND (brand_name) WITH PARSER ngram;

CREATE TABLE owned_items
(
    id                 int PRIMARY KEY AUTO_INCREMENT,
    user_id            int,
    item_size_price_id int,
    purchase_price     int
)Engine=InnoDB CHARACTER SET utf8mb4;



ALTER TABLE address
    ADD FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE payment_card
    ADD FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE sales_account
    ADD FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE notification
    ADD FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE notification
    ADD FOREIGN KEY (interested_item_id) REFERENCES interested_item (id);

ALTER TABLE item
    ADD FOREIGN KEY (brand_id) REFERENCES brand (id);

ALTER TABLE item
    ADD FOREIGN KEY (manager_id) REFERENCES manager (id);

ALTER TABLE item_size_price
    ADD FOREIGN KEY (item_id) REFERENCES item (id);

ALTER TABLE item_img
    ADD FOREIGN KEY (item_id) REFERENCES item (id);

ALTER TABLE deal
    ADD FOREIGN KEY (item_id) REFERENCES item (id);

ALTER TABLE deal
    ADD FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE interested_item
    ADD FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE interested_item
    ADD FOREIGN KEY (item_size_price_id) REFERENCES item_size_price (id);

ALTER TABLE owned_items
    ADD FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE owned_items
    ADD FOREIGN KEY (item_size_price_id) REFERENCES item_size_price (id);
