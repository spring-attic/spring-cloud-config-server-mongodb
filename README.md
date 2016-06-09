# Spring Cloud Config Server MongoDB
Spring Cloud Config Server MongoDB enables seamless integration of the regular Spring Cloud Config Server with MongoDB to manage external properties for applications across all environments.

# Quick Start
Create a standard Spring Boot application, like this:

```
@SpringBootApplication
@EnableMongoConfigServer
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
```

Configure the application's `spring.data.mongodb.*` properties in `application.yml`, like this:
```
spring:
  data:
    mongodb:
      uri: mongodb://localhost/config-db
```

Add documents to the `config-db` mongo database, like this:
```
use config-db;

db.gateway.insert({
  "label": "master",
  "profile": "prod",
  "source": {
    "user": {
      "max-connections": 1,
      "timeout-ms": 3600
    }
  }
});
```
In the above snippet we've configured properties for an application named `gateway` having profile `prod` and label `master`.

The `application-name` is identified by the collection's `name` and a MongoDB document's `profile` and `label` values represent the Spring application's `profile` and `label` respectively. Note that documents with no `profile` or `label` values will have them considered `default`. All properties must be listed under the `source` key of the document.

Finally, access these properties by invoking `http://localhost:8080/master/gateway-prod.properties`. The response would be like this:
```
user.max-connections: 1.0
user.timeout-ms: 3600.0
```

# References
[spring-cloud-config](https://github.com/spring-cloud/spring-cloud-config)
