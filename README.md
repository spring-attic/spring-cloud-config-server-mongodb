# Spring Cloud Config Server MongoDB

[![build-status](https://travis-ci.org/spring-cloud-incubator/spring-cloud-config-server-mongodb.svg?branch=master)](https://travis-ci.org/spring-cloud-incubator/spring-cloud-config-server-mongodb)
[![Join the chat at https://gitter.im/spring-cloud-incubator/spring-cloud-config-server-mongodb](https://badges.gitter.im/spring-cloud-incubator/spring-cloud-config-server-mongodb.svg)](https://gitter.im/spring-cloud-incubator/spring-cloud-config-server-mongodb?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

Spring Cloud Config Server MongoDB enables seamless integration of the regular Spring Cloud Config Server with MongoDB to manage external properties for applications across all environments.

# Quick Start
Configure pom.xml, like this:
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-config-server-mongodb</artifactId>
        <version>1.0.0.BUILD-SNAPSHOT</version>
    </dependency>
</dependencies>

<repositories>
    <repository>
        <id>spring-snapshots</id>
        <name>Spring Snapshots</name>
        <url>https://repo.spring.io/libs-snapshot-local</url>
        <snapshots>
            <enabled>true</enabled>
        </snapshots>
    </repository>
    <repository>
        <id>ojo-snapshots</id>
        <name>OJO Snapshots</name>
        <url>https://oss.jfrog.org/artifactory/libs-snapshot</url>
        <snapshots>
            <enabled>true</enabled>
        </snapshots>
    </repository>
</repositories>
```

Create a standard Spring Boot application, like this:
```java
@SpringBootApplication
@EnableMongoConfigServer
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
```

Configure the application's `spring.data.mongodb.*` properties in `application.yml`, like this:
```yaml
spring:
  data:
    mongodb:
      uri: mongodb://localhost/config-db
```

Add documents to the `config-db` mongo database, like this:
```javascript
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
```json
user.max-connections: 1.0
user.timeout-ms: 3600.0
```

# References
[spring-cloud-config](https://github.com/spring-cloud/spring-cloud-config)
