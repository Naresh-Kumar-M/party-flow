package com.example.validation.address;

import java.io.IOException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.example.validation.address.event.AddressValidationRequestEvent;
import com.example.validation.address.event.Event;
import com.example.validation.client.AddressValidationClient;

@Service
public class AddressValidationEventConsumer {

  private final AddressValidationClient addressValidationClient;

  public AddressValidationEventConsumer(final AddressValidationClient addressValidationClient) {
    this.addressValidationClient = addressValidationClient;
  }

  @KafkaListener(topics = "${validation-service.request.topic-name}",
      groupId = "${spring.kafka.consumer.group-id}",
      containerFactory = "AddressValidationKafkaListenerFactory")
  public void consume(Event message) throws IOException {
    addressValidationClient.validateAddress((AddressValidationRequestEvent)message);
  }

}
