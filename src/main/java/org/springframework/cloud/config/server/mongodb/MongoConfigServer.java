package org.springframework.cloud.config.server.mongodb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableMongoConfigServer
public class MongoConfigServer {

    public static void main(String args[]) {
        SpringApplication.run(MongoConfigServer.class, args);
    }
}
