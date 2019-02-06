/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.config.server.mongodb;

import static org.junit.Assert.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.config.server.mongodb.environment.MongoEnvironmentRepository.MongoPropertySource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Venil Noronha
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
	classes = MongoConfigServerTests.MongoConfigServerApplication.class,
	webEnvironment = RANDOM_PORT
)
public class MongoConfigServerTests {

	@LocalServerPort
	private int port;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Test
	public void server() {
		mongoTemplate.dropCollection("testapp");
		MongoPropertySource ps = new MongoPropertySource();
		ps.getSource().put("testkey", "testval");
		mongoTemplate.save(ps, "testapp");
		Environment environment = new TestRestTemplate().getForObject("http://localhost:"
				+ port + "/testapp/default", Environment.class);
		assertEquals("testapp-default", environment.getPropertySources().get(0).getName());
		assertEquals(1, environment.getPropertySources().size());
		assertEquals(true, environment.getPropertySources().get(0).getSource().containsKey("testkey"));
		assertEquals("testval", environment.getPropertySources().get(0).getSource().get("testkey"));
	}

	@SpringBootApplication
	@EnableMongoConfigServer
	public static class MongoConfigServerApplication {

		public static void main(String args[]) {
			SpringApplication.run(MongoConfigServerApplication.class, args);
		}

	}

}
