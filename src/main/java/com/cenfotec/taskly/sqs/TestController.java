package com.cenfotec.taskly.sqs;

import com.amazonaws.services.sqs.model.Message;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sqs")
public class TestController {

    private static final Logger LOG = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private QueueMessagingTemplate queueMessagingTemplate;

    @Value("${cloud.aws.end-point.uri}")
    private String sqsEndPoint;

    @GetMapping
    public void sendMessage() {
        Message msg = new Message();
        msg.setMessageId("1");
        msg.setBody("Test Body");
        msg.addAttributesEntry("REQUEST_TYPE", "POST");
        msg.addAttributesEntry("REQUEST_DOMAIN", "USER");
        queueMessagingTemplate.convertAndSend(sqsEndPoint, msg);
    }

    @SqsListener("Taskly")
    public void getMessage(Message message) {
        LOG.info("SQS Message: " + message);
        final String REQUEST_TYPE = message.getAttributes().get("REQUEST_TYPE");
        final String REQUEST_DOMAIN = message.getAttributes().get("REQUEST_DOMAIN");
        final String BODY_JSON = message.getBody();

        LOG.info("REQUEST_TYPE: " + REQUEST_TYPE);
        LOG.info("REQUEST_DOMAIN: " + REQUEST_DOMAIN);
        LOG.info("BODY_JSON: " + BODY_JSON);

    }
}
