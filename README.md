# Spring Cloud Config Server MongoDB

[![build-status](https://travis-ci.org/spring-cloud-incubator/spring-cloud-config-server-mongodb.svg?branch=master)](https://travis-ci.org/spring-cloud-incubator/spring-cloud-config-server-mongodb)
[![Join the chat at https://gitter.im/spring-cloud-incubator/spring-cloud-config-server-mongodb](https://badges.gitter.im/spring-cloud-incubator/spring-cloud-config-server-mongodb.svg)](https://gitter.im/spring-cloud-incubator/spring-cloud-config-server-mongodb?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

Spring Cloud Config Server MongoDB enables seamless integration of the regular Spring Cloud Config Server with MongoDB to manage external properties for applications across all environments.

# Quick Start
Configure pom.xml, like this:
```
<dependencies>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-config-server-mongodb</artifactId>
        <version>0.0.1.BUILD-SNAPSHOT</version>
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

You can run mongo db with docker.

```
docker run --name config-server-mongo -d -p 27017:27017 mongo

docker exec -it config-server-mongo mongo admin
```

The `application-name` is identified by the collection's `name` and a MongoDB document's `profile` and `label` values represent the Spring application's `profile` and `label` respectively. Note that documents with no `profile` or `label` values will have them considered `default`. All properties must be listed under the `source` key of the document.

Finally, access these properties by invoking `http://localhost:8080/master/gateway-prod.properties`. The response would be like this:
```
user.max-connections: 1.0
user.timeout-ms: 3600.0
```

# References
[spring-cloud-config](https://github.com/spring-cloud/spring-cloud-config)

# Current Issue

I modified the spring-cloud-config-server-mongodb project to use the latest version of Spring Boot (1.5.3.RELEASE) and  Spring Cloud Config (1.3.0.RELEASE).
I also introduced the class MongoConfigServer that runs the mongo db config server.

When I run it, I get this error:

```
org.springframework.beans.factory.NoUniqueBeanDefinitionException: 
No qualifying bean of type 
'org.springframework.cloud.config.server.environment.EnvironmentRepository' available: 
more than one 'primary' bean found among candidates: [searchPathLocator, environmentRepository, searchPathCompositeEnvironmentRepository]
```

I am not sure what I did wrong.  How do I write my own config server without getting this error?
I don't see any documentation on how to do this.

Can somebody please help me, or guide me?

This mongo db config server used to work with older versions of Spring Boot and Spring Cloud Config.
I am using this Mongo DB Config Server as an example for writing a different Config Server, which is also receiving the same error.

My fork of spring-cloud-config-server-mongodb is available at [https://github.com/minmay/spring-cloud-config-server-mongodb.git](https://github.com/minmay/spring-cloud-config-server-mongodb.git)



