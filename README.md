# Microservices with Java and Spring

This project is showcase for using Java and Spring when developing microservices.
Showcase consists of several services.

## Services

#### Config service
Config service provides support for externalized configuration in a distributed system. It is a central place 
to manage external properties for applications across all environments. For simplicity’s sake it is using properties 
from classpath (usually in production something else is used like Git or Vault).

#### Discovery service
Service discovery is one of the key tenets of a microservice-based architecture.
Trying to hand-configure each client or some form of convention can be difficult to do and can be brittle.
Discovery is useful because it enables client-side load-balancing and decouples service providers from 
consumers without the need for DNS.

#### Gateway service
Spring Cloud Gateway application to provide a simple, effective way to route to APIs and provide
cross-cutting concerns to them such as: security, monitoring/metrics, and resiliency.

#### Order service
Imaginary service that can create order. When creating order, service calls item service to get item prices
so that it can calculate total order amount. This call is guarded by circuit breaker. When order is created, 
event is published to Kafka. Orders are saved to PostgreSQL database.

#### Item service
Imaginary service that give info about items like name and price. It is using redis to persist item information.
If items does not exist it is generated and stored (for simplicity’s sake).

#### Billing service
Imaginary service that will "reduce" customer's balance (it will actually just log imaginary balance decrease)
when event is received from Kafka.

#### Admin service
Admin is a Spring Boot Admin (web) application, used for managing and monitoring Spring Boot applications. 
It's an easy way to get insight to logs, metrics and some JVM stats for this showcase project. Usually in production
something like Datadog or combination of services like Elasticsearch, Prometheus and Grafana would be used.

## Running example

##### Preconditions
- Installed Docker (with Compose)
- Installed Java 17 JDK

First we need to build project first by running:

```sh
./mvnw clean install
```

To make running showcase as seamless as possible Docker Compose file is defined which enables running
all necessary services (PostgreSQL, Redis, Kafka...) and Spring applications. To start them all run:
```sh
docker compose up
```

Give some time for services to catch up with one another and then execute something like following:

```sh
curl --location --request POST 'http://localhost:8080/order-service/orders/' \
--header 'Content-Type: application/json' \
--data-raw '{
    "customerUuid": "98c88eb9-36e1-4750-a9a7-abd176457c50",
    "items": [
        {
            "uuid": "68cb3320-f0c2-4fcb-bdd2-ce26d6e34a27",
            "count": 3
        },
        {
            "uuid": "3f160c35-477e-42d1-92b5-ac0503af7a23",
            "count": 2
        }
    ]
}'
```
If Gateway still doesn't know about order service you will receive HTTP status 503. Wait a bit more then.

All uuids in example can be random. You can change number and count of items.

When curl is ran, check the logs. Order, item and billing service will contain logs and all of them will
have same trace id because all services are configured to use distributed tracing. Note that trace was preserved even
for billing service which is using asynchronous communication (Kafka).

If you want to test circuit breaker shut down item service by running:
```sh
docker container stop $(docker container ls | grep item-service | grep -Eo '^[^ ]+')
```
Now execute few requests again. You will receive HTTP status 502 (indicating downstream service is down)
Note that in the beginning request will take longer (timeout is 2 seconds) and after circuit breaker kicks in
and call to item service is not made anymore, requests will be pretty short.

To start item service again run:
```sh
docker container start $(docker container ls -a | grep item-service | grep -Eo '^[^ ]+')
```

Check out [admin service ui](http://localhost:8100/applications).
