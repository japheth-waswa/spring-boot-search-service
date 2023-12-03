package com.japethwaswa.springelasticsearch.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.japethwaswa.springelasticsearch.ElasticSearchApplication;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "*")
public class ElasticSearchCustomClient {

    @Bean
    public ElasticsearchClient restClient() {
        // Create the low-level client
        RestClient httpClient = RestClient.builder(
                new HttpHost(ElasticSearchApplication.dotenv != null ? ElasticSearchApplication.dotenv.get("ELASTIC_SEARCH_HOST") : "localhost",
                        ElasticSearchApplication.dotenv != null ? Integer.parseInt(ElasticSearchApplication.dotenv.get("ELASTIC_SEARCH_PORT")) : 9200)
        ).build();

        // Create the Java API Client with the same low level client
        ElasticsearchTransport transport = new RestClientTransport(
                httpClient,
                new JacksonJsonpMapper()
        );

        return new ElasticsearchClient(transport);
    }
}
