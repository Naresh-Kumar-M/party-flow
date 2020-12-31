package com.example.validation.address;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.example.validation.address.event.AddressValidationResponseEvent;
import com.example.validation.address.event.Event;

@Service
public class AddressValidationResponseProducer {

  private final String validationResponseTopicName;
  private final KafkaTemplate<String, Event> template;

  public AddressValidationResponseProducer(final KafkaTemplate<String, Event> template,
      @Value("${validation-service.response.topic-name}") String validationRequestTopicName) {
    this.template = template;
    this.validationResponseTopicName = validationRequestTopicName;
  }

  public void generateValidationResponseEvent(boolean status, String requestId, String address) {
    template.send(validationResponseTopicName,
        new AddressValidationResponseEvent(status, requestId, address));
  }
}
