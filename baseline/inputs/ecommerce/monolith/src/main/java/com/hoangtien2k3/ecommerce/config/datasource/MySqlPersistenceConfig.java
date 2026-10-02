package com.hoangtien2k3.ecommerce.config.datasource;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(
        basePackages = {
                "com.hoangtien2k3.ecommerce.repository.user",
                "com.hoangtien2k3.ecommerce.repository.order",
                "com.hoangtien2k3.ecommerce.repository.product",
                "com.hoangtien2k3.ecommerce.repository.payment",
                "com.hoangtien2k3.ecommerce.repository.inventory",
                "com.hoangtien2k3.ecommerce.repository.favourite",
                "com.hoangtien2k3.ecommerce.repository.shipping"
        },
        entityManagerFactoryRef = "mysqlEntityManagerFactory",
        transactionManagerRef = "mysqlTransactionManager"
)
public class MySqlPersistenceConfig {

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "app.datasource.mysql")
    public DataSource mysqlDataSource() {
        HikariDataSource dataSource = DataSourceBuilder.create().type(HikariDataSource.class).build();
        dataSource.setMaximumPoolSize(12);
        dataSource.setMinimumIdle(2);
        return dataSource;
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean mysqlEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("mysqlDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages(
                        "com.hoangtien2k3.ecommerce.model.user",
                        "com.hoangtien2k3.ecommerce.model.order",
                        "com.hoangtien2k3.ecommerce.model.product",
                        "com.hoangtien2k3.ecommerce.model.payment",
                        "com.hoangtien2k3.ecommerce.model.inventory",
                        "com.hoangtien2k3.ecommerce.model.favourite",
                        "com.hoangtien2k3.ecommerce.model.shipping"
                )
                .persistenceUnit("mysql")
                .properties(mysqlJpaProperties())
                .build();
    }

    @Bean
    @Primary
    public PlatformTransactionManager mysqlTransactionManager(
            @Qualifier("mysqlEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    private Map<String, Object> mysqlJpaProperties() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        properties.put("hibernate.show_sql", false);
        return properties;
    }
}
