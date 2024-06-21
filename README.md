## Building reliable Transactional with outbox pattern in Spring boot.
##### Ref: https://medium.com/@syedharismasood4/building-reliable-microservices-with-the-transactional-outbox-pattern-in-spring-boot-952d96f8b534

## Problem Statement
The Dual Write problem occurs when a piece of code tries to write to two different data sources i.e. a database and a message queue in a single transaction and one of those writes fail leaving the system in an inconsistent state. In order to better understand the problem, let’s consider the following piece of code:

    @Transactional
    public User createUser(UserRequestDto userRequestDto) {
        User user = userRequestDtoMapper.mapToUserEntity(userRequestDto);

        user = userRepository.save(user); // Write to the database

        kafkaTemplate.send(topic, user); // Write to the message queue

        return user;
    }
Although the above code snippet apparently seems fine, but after having a closer look at it, we can find out that there are some issues with that. The first one is what if the Kafka broker became temporarily unavailable during the transaction? Yes, as you might think we can simply roll-back transaction, well yes we can, but wait is it a good practice to roll-back the entire transaction due to a temporary failure of an external system? Definitely the answer would be no.

The second problem which is not so easy to detect by looking at the code is, what if transaction fails to commit? Well again you might think that this isn’t going to happen, well on your local machine it’s true that this scenario will hardly occur, but on production there are thousands of reasons that can cause the database commit to fail. If you are still not convinced then this stackoverflow thread might convince you otherwise. And if this failure occurs then we’ll end up in an inconsistent state because the message will be sent before the transaction is rolled-back. So, essentially user will not be persisted in the database but message will be published indicating that user was created when in reality it was not.

## Solution
In order to solve this problem we have two solutions one is to implement distributed transaction pattern like Saga. But, the problem is that it’s a bit difficult to implement and maintain. The good news is we can avoid distributed transactions (in most cases) by using Transactional Outbox Pattern.

Transactional Outbox Pattern
The idea of Transactional Outbox is that in order to avoid Dual Write problem instead of writing to two different data sources we must write to only one i.e. our database in a single transaction, so we can roll-back the entire transaction in case of failures as a single unit without side effects. So, essentially we are creating a local transaction instead of a distributed one. In this way we will always end up in a consistent state. In order to implement this pattern we have to create a new table called outbox and instead of writing directly to a queue we will write the message in this table. And create a separate Message Relay Service (MRS) which will poll undelivered messages and deliver them in batches. Another advantage of this pattern is that since messages are stored in the outbox table, we can also easily replay messages incase required. Diagrammatically this can be viewed as follows:




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

## There are multiple ways to implement MRS. Few of them are:
- Create a job that will run periodically and poll the outbox table for undelivered messages and deliver them in batches.
- Use CDC (change data capture) tools like Debezium to automatically publish messages whenever data is inserted in the outbox table.
