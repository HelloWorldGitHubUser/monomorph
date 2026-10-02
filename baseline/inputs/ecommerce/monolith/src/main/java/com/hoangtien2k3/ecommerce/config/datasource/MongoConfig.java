package com.hoangtien2k3.ecommerce.config.datasource;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.hoangtien2k3.ecommerce.repository.notification")
public class MongoConfig {
}
