package com.esmyrna.desafio_anota_ai.services.aws;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.Topic;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AwsSnsService {
    @Autowired
    AmazonSNS snsClient;
    @Autowired
    Topic catalogTopic;

    public AwsSnsService(AmazonSNS snsClient, @Qualifier("catalogEventsTopic") Topic catalogTopic)  {
        this.snsClient = snsClient;
        this.catalogTopic = catalogTopic;
    }

    public void publish(MessageDTO messageDTO) {
        this.snsClient.publish(catalogTopic.getTopicArn(), messageDTO.message());
    }
}
