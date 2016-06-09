# Spring Cloud Config Server MongoDB
Spring Cloud Config Server MongoDB enables seamless integration of the regular Spring Cloud Config Server with MongoDB to manage external properties for applications across all environments.

# Configuration
Just add `@EnableMongoConfigServer` to your `@Configuration` class and set up basic `spring.data.mongodb.*` properties for your Spring Boot application and you're ready to go. Properties stored in MongoDB will have their `application-name` determined by the collection's name, while 'profile' and 'label' key values of a MongoDB Document would represent the Spring `profile` and `label` respectively.

MongoDB properties for an application named `gateway` would have the properties stored as a document in a collection named 'gateway'. A sample properties document could look as below.

```
label: master
profile: prod
users: {
  max-connections: 1,
  block-time-ms: 3600
}
```

You could then access the properties by invoking the `http://localhost:8888/master/gateway-prod.properties`.

# References
[spring-cloud-config](https://github.com/spring-cloud/spring-cloud-config)
