package com.example.party.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import com.example.party.repository.PartyRequestRepository;
import com.example.party.repository.entity.PartyRequestEntity;
import com.example.party.repository.entity.RequestStatus;
import com.example.party.service.PartyService;
import com.example.party.service.notification.PartyNotificationEventProducer;

@RestController
@RequestMapping("approval")
public class ApprovalController {

  @Autowired
  private PartyRequestRepository partyRequestRepository;

  @Autowired
  private PartyService partyService;

  @Autowired
  private PartyNotificationEventProducer notificationProducer;

  @PostMapping("/{requestId}/{status}")
  public void approveRequests(@PathVariable("requestId") String requestId,
      @PathVariable("status") String status) {
    Optional<PartyRequestEntity> partyRequest = partyRequestRepository.findById(requestId);
    if (partyRequest.isEmpty()) {
      throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
    }
    PartyRequestEntity request = partyRequest.get();
    RequestStatus requestStatus =
        status.equalsIgnoreCase("SUCCESS") ? RequestStatus.APPROVAL_SUCCESS
            : RequestStatus.APPROVAL_FAILED;
    request.setStatus(requestStatus);
    partyRequestRepository.save(request);

    if (request.getStatus() == RequestStatus.APPROVAL_SUCCESS) {
      partyService.createParty(requestId);
      return;
    }
    notificationProducer.generateNotificationEvent(request);
  }

}
