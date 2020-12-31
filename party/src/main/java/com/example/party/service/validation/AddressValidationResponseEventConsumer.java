package com.example.party.service.validation;

import java.io.IOException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.example.party.repository.PartyRequestRepository;
import com.example.party.repository.entity.PartyRequestEntity;
import com.example.party.repository.entity.RequestStatus;
import com.example.party.service.PartyRequestService;
import com.example.party.service.event.AddressValidationResponseEvent;

@Service
public class AddressValidationResponseEventConsumer {

  @Autowired
  private PartyRequestRepository partyRequestRepository;

  @Autowired
  private PartyRequestService partyRequestService;

  @KafkaListener(topics = "${validation-service.response.topic-name}",
      groupId = "${spring.kafka.consumer.group-id}",
      containerFactory = "AddressValidationKafkaListenerFactory")
  public void consume(AddressValidationResponseEvent message) throws IOException {
    Optional<PartyRequestEntity> partyRequest =
        partyRequestRepository.findById(message.getRequestId());
    if (partyRequest.isPresent()) {
      PartyRequestEntity request = partyRequest.get();
      request.setStatus(RequestStatus.ADDRESS_VALIDATED);
      partyRequestRepository.save(request);
      partyRequestService.processAddressValidatedPartyRequest(message.getRequestId());
    }
  }
}
