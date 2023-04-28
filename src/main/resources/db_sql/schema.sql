drop all objects;

CREATE TABLE `users`
(
    `id`                      int PRIMARY KEY AUTO_INCREMENT,
    `email`                   varchar(255) UNIQUE,
    `password`                varchar(255),
    `fourteen_agreement`      boolean,
    `gender`                  varchar(255),
    `ad_agreement`            boolean,
    `personal_authentication` boolean,
    `phone_number`            varchar(255),
    `profile_name`            varchar(255),
    `name`                    varchar(255),
    `rank`                    varchar(255),
    `profile_img_name`        varchar(255),
    `profile_img_url`         varchar(255),
    `profile_img_origin_name` varchar(255),
    `created_at`              timestamp,
    `modified_at`             timestamp
);

CREATE TABLE `manager`
(
    `id`              int PRIMARY KEY AUTO_INCREMENT,
    `employee_number` varchar(255),
    `password`        varchar(255),
    `name`            varchar(255),
    `phone_number`    varchar(255),
    `agreement`       boolean,
    `created_at`      timestamp,
    `modified_at`     timestamp
);

CREATE TABLE `address`
(
    `id`              int PRIMARY KEY AUTO_INCREMENT,
    `user_id`         int,
    `name`            varchar(255),
    `phone_number`    varchar(255),
    `zipcode`         varchar(255),
    `detail1`         varchar(255),
    `detail2`         varchar(255),
    `default_address` boolean,
    `created_at`      timestamp
);

CREATE TABLE `payment_card`
(
    `id`           int PRIMARY KEY AUTO_INCREMENT,
    `user_id`      int,
    `card_company` varchar(255),
    `card_number`  varchar(255),
    `expiration`   varchar(255),
    `card_pw`      varchar(255),
    `created_at`   timestamp
);

CREATE TABLE `sales_account`
(
    `id`             int PRIMARY KEY AUTO_INCREMENT,
    `user_id`        int,
    `bank_name`      varchar(255),
    `account_number` varchar(255),
    `account_holder` varchar(255),
    `created_at`     timestamp
);

CREATE TABLE `notification`
(
    `id`                 int PRIMARY KEY AUTO_INCREMENT,
    `user_id`            int,
    `item_id`            int,
    `deal_id`            int,
    `title`             varchar(255),
    `context`           varchar(255),
    `notification_type`  varchar(255),
    `created_at`         timestamp
);

CREATE TABLE `item`
(
    `id`                   int PRIMARY KEY AUTO_INCREMENT,
    `item_name`            varchar(255),
    `model_number`         varchar(255),
    `category_id`          int,
    `detailed_category_id` int,
    `gender`               varchar(255),
    `release_date`         timestamp,
    `representative_color` varchar(255),
    `released_price`       int,
    `latest_price`         int,
    `brand_id`             int,
    `created_at`           timestamp,
    `modified_at`          timestamp,
    `manager_id`           int
);

CREATE TABLE `item_category`
(
    `id`                 int PRIMARY KEY AUTO_INCREMENT,
    `category_name`      varchar(255) unique,
    `parent_category_id` int,
    foreign key (`parent_category_id`) references ITEM_CATEGORY (id) on delete set null
);


CREATE TABLE `item_size_price`
(
    `id`                     int PRIMARY KEY AUTO_INCREMENT,
    `item_id`                int,
    `size`                   varchar(255),
    `lowest_selling_price`   int,
    `highest_purchase_price` int,
    `modified_at`            timestamp
);

CREATE TABLE `item_img`
(
    `id`                    int PRIMARY KEY AUTO_INCREMENT,
    `item_id`               int,
    `img_name`              varchar(255),
    `img_url`               varchar(255),
    `origin_name`           varchar(255),
    `is_representative_img` boolean,
    `created_at`            timestamp
);

CREATE TABLE `deal`
(
    `id`                 int PRIMARY KEY AUTO_INCREMENT,
    `item_id`            int,
    `deal_type`       varchar(255),
    `user_id`            int,
    `price`              int,
    `size`               varchar(255),
    `period`             timestamp,
    `utilization_policy` boolean,
    `sales_condition`    boolean,
    `status`             varchar(255),
    `other_id`           int,
    `created_at`         timestamp,
    `trading_day`        timestamp
);

CREATE TABLE `FCM_token`
(
    `user_id`            int primary key,
    `token`              varchar(255),
    foreign key (`user_id`) references USERS (id)
);

CREATE TABLE `interested_item`
(
    `id`                 int PRIMARY KEY AUTO_INCREMENT,
    `user_id`            int,
    `item_size_price_id` int
);

CREATE TABLE `brand`
(
    `id`         int PRIMARY KEY AUTO_INCREMENT,
    `brand_name` varchar(255),
    `is_luxury`  boolean
);

CREATE TABLE `owned_items`
(
    `id`                 int PRIMARY KEY AUTO_INCREMENT,
    `user_id`            int,
    `item_size_price_id` int,
    `purchase_price`     int
);

ALTER TABLE `address`
    ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `payment_card`
    ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `sales_account`
    ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `notification`
    ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `notification`
    ADD FOREIGN KEY (`item_id`) REFERENCES `item` (`id`);

ALTER TABLE `notification`
    ADD FOREIGN KEY (`deal_id`) REFERENCES `deal` (`id`);

ALTER TABLE `item`
    ADD FOREIGN KEY (`brand_id`) REFERENCES `brand` (`id`);

ALTER TABLE `item`
    ADD FOREIGN KEY (`manager_id`) REFERENCES `manager` (`id`);

ALTER TABLE `item_size_price`
    ADD FOREIGN KEY (`item_id`) REFERENCES `item` (`id`);

ALTER TABLE `item_img`
    ADD FOREIGN KEY (`item_id`) REFERENCES `item` (`id`);

ALTER TABLE `deal`
    ADD FOREIGN KEY (`item_id`) REFERENCES `item` (`id`);

ALTER TABLE `deal`
    ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `interested_item`
    ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `interested_item`
    ADD FOREIGN KEY (`item_size_price_id`) REFERENCES `item_size_price` (`id`);

ALTER TABLE `owned_items`
    ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `owned_items`
    ADD FOREIGN KEY (`item_size_price_id`) REFERENCES `item_size_price` (`id`);
