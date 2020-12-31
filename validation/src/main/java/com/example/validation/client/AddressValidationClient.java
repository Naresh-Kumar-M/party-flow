package com.example.validation.client;

import org.springframework.stereotype.Component;
import com.example.validation.address.AddressValidationResponseProducer;
import com.example.validation.address.event.AddressValidationRequestEvent;

@Component
public class AddressValidationClient {

  private final AddressValidationResponseProducer addressValidationResponseProducer;

  public AddressValidationClient(
      final AddressValidationResponseProducer addressValidationResponseProducer) {
    this.addressValidationResponseProducer = addressValidationResponseProducer;
  }

  public void validateAddress(AddressValidationRequestEvent message) {
    // TODO business logic to connect to address doctor and check is Valid address or not
    addressValidationResponseProducer.generateValidationResponseEvent(true, message.getRequestId(),
        message.getAddress());
  }

}
