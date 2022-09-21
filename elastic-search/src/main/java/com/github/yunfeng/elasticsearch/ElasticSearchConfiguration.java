package com.github.yunfeng.elasticsearch;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.message.BasicHeader;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import javax.net.ssl.SSLContext;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.github.yunfeng")
@Slf4j
public class ElasticSearchConfiguration {
    @Bean
    public RestHighLevelClient createSimpleElasticClient() throws Exception {
        try {
            CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("elastic", "es-password"));

            SSLContextBuilder sslBuilder = SSLContexts.custom().loadTrustMaterial(null, (x509Certificates, s) -> true);
            SSLContext sslContext = sslBuilder.build();
            RestClientBuilder restClientBuilder = RestClient.builder(new HttpHost("localhost", 9200, "https")).setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder.setSSLContext(sslContext).setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE).setDefaultCredentialsProvider(credentialsProvider)).setRequestConfigCallback(requestConfigBuilder -> requestConfigBuilder.setConnectTimeout(5000).setSocketTimeout(120000)).setDefaultHeaders(this.compatibilityHeaders());
            RestHighLevelClient client = new RestHighLevelClient(restClientBuilder);
            System.out.println("elasticsearch client created");
            return client;
        } catch (Exception e) {
            log.error("createSimpleElasticClient raise exception:", e);
            throw new Exception("Could not create an elasticsearch client!!");
        }
    }

    private Header[] compatibilityHeaders() {
        // https://stackoverflow.com/questions/48842352/elasticsearch-java-resthighlevelclient-unable-to-parse-response-body-illegalar
        return new Header[]{new BasicHeader(HttpHeaders.ACCEPT, "application/vnd.elasticsearch+json;compatible-with=7"), new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/vnd.elasticsearch+json;compatible-with=7")};
    }
}