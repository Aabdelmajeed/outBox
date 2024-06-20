## Building reliable Transactional with outbox pattern in Spring boot.
##### Ref: https://medium.com/@syedharismasood4/building-reliable-microservices-with-the-transactional-outbox-pattern-in-spring-boot-952d96f8b534


## DB quires for User and Outbox table

```sql
CREATE TABLE  `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `firstname` varchar(45) NOT NULL,
  `lastname` varchar(45) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `dob` date NOT NULL,
  `email` varchar(255) NOT NULL,
  `created_date` timestamp NULL DEFAULT NULL,
  `last_modified_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `outbox` (
  `id` int NOT NULL AUTO_INCREMENT,
  `aggregate` varchar(10) NOT NULL,
  `message` text NOT NULL,
  `is_delivered` tinyint NOT NULL DEFAULT '0',
  `created_date` timestamp NOT NULL,
  `last_modified_date` timestamp NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
```
