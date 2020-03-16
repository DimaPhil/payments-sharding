# Payments sharding application

Implementation for sharding bank payments into several database instances and then retrieving them.

## Sharding

Sharding is implemented using Consistent Hashing algorithm (because this way it'd easier to add/remove shard in runtime):
* we use 10 copies of each database instance (it is configurable in `application.yaml` if needed)
* Hash codes of shard objects are used to calculate where the shard should be placed on the circle.
* For each payment `<fromId, toId, amount>` we use `fromId.hashCode()` as a sharding key, because we want all payers with the same id to be stored on the same instance

## API

Swagger is enabled for this application, so Swagger schema is available as usual on `/swagger-ui.html`. Also already pre-built swagger schema is available in the `build` directory.

API contains three endpoints:
* Adding a new payment (it is being sharded and saved on one of the available MySQL instances then);
* Adding a list of payments;
* Calculating the number of money spent for a given account identifier.

# Known cons

* I didn't have much time to investigate how to setup any framework (Hibernate, jOOQ or something similar) with multiple datasources at once, so I implemented it just using raw queries
    * Connection pools are still used (using DBCP2)
    * There should be no SQL injections, because only internal integer numbers are being injected to the query
* The same as above, I didn't have time to investigate how to set up Flyway or Liquibase on multiple data sources environment, so right now `payment` table in the database should be created manually
* It'd better to move logic for requesting data from the specific shard to a separate module/microservice - orchestrator, so that application knows nothing about shards and how everything works there, and only orchestrator manipulates with data and shards (we solve SRP problem this way)

# Testing

* For `payments-sharding-dto` and `payments-sharding-core` modules I've implemented unit tests
    * Coverage is almost 100% in both modules
* For `payments-sharding-api` I tested everything locally using 3 local instances of MySQL

# Setup

To setup the environment, you need:
* Configure and set up mysql instances which will be used as shards
    * In default configuration there are 3 mysql instances, but any number of instances is supported
    * MySQL instances and their credentials are configured in resources (`application.yaml` resource file)
    * I personally start MySQL instances in Docker for testing:
    ```bash
    docker run -d --name=mysql-instance-1 -p 3307:3306 -e MYSQL_ROOT_PASSWORD=1 mysql
    docker run -d --name=mysql-instance-2 -p 3308:3306 -e MYSQL_ROOT_PASSWORD=1 mysql
    docker run -d --name=mysql-instance-3 -p 3309:3306 -e MYSQL_ROOT_PASSWORD=1 mysql
    ```
* Build application:
```bash
mvn clean install enforcer:enforce
```
* If you are not able to build application for some reason, feel free to use pre-built jar from the `build` directory

# Run

To run application, just run the jar-file:
```bash
java -jar payments-sharding-api-0.0.1-SNAPSHOT.jar
```

It starts the server on port 8080, you can send requests using Swagger or via Postman or any other tool after that.
