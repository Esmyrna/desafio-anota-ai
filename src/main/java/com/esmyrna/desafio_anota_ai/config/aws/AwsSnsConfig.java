package com.esmyrna.desafio_anota_ai.config.aws;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.Topic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsSnsConfig {
    @Value("${aws.region}")
    private String region;
    @Value("${AWS_ACCESS_KEY}")
    private String accessKey;
    @Value("${AWS_SECRET_KEY}")
    private String secretKey;
    @Value("${aws.sns.topic.catalog.arn}")
    private String catalogTopicArn;

    @Bean
    public AmazonSNS AmazonSNSBuilder() {
        BasicAWSCredentials credendials =  new BasicAWSCredentials(accessKey, secretKey);

        return AmazonSNSClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credendials))
                .withRegion(region)
                .build();
    }

    @Bean(name = "catalogEventsTopic")
    public Topic snsCatalogTopicBuilder() {
        return new Topic().withTopicArn(catalogTopicArn);
    }

}
