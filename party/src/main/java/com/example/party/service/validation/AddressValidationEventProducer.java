package com.example.party.service.validation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.example.party.service.event.AddressValidationRequestEvent;
import com.example.party.service.event.Event;

@Service
public class AddressValidationEventProducer {

  private final String validationRequestTopicName;
  private final KafkaTemplate<String, Event> template;

  public AddressValidationEventProducer(final KafkaTemplate<String, Event> template,
      @Value("${validation-service.request.topic-name}") String validationRequestTopicName) {
    this.template = template;
    this.validationRequestTopicName = validationRequestTopicName;
  }

  public void generateValidationEvent(String requestId, String address) {
    template.send(validationRequestTopicName,
        new AddressValidationRequestEvent(requestId, address));
  }
}
