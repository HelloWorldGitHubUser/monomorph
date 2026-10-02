package com.hoangtien2k3.ecommerce.config.datasource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.util.StringUtils;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.hoangtien2k3.ecommerce.repository.search")
public class ElasticsearchConfig extends ElasticsearchConfiguration {

    @Value("${elasticsearch.url:localhost:9200}")
    private String url;

    @Value("${elasticsearch.username:}")
    private String username;

    @Value("${elasticsearch.password:}")
    private String password;

    @Override
    public ClientConfiguration clientConfiguration() {
        var builder = ClientConfiguration.builder()
                .connectedTo(url)
                .withConnectTimeout(10000)
                .withSocketTimeout(10000);

        if (StringUtils.hasText(username) && StringUtils.hasText(password)) {
            builder.withBasicAuth(username, password);
        }

        return builder.build();
    }
}
