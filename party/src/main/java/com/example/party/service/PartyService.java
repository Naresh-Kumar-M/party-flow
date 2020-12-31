package com.example.party.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.party.repository.PartyRepository;
import com.example.party.repository.PartyRequestRepository;
import com.example.party.repository.entity.PartyEntity;
import com.example.party.repository.entity.PartyRequestEntity;
import com.example.party.repository.entity.RequestStatus;
import com.example.party.service.notification.PartyNotificationEventProducer;
import com.example.party.service.vo.PartyRequestVO;

@Service
public class PartyService {

  @Autowired
  private PartyRepository partyRepository;

  @Autowired
  private PartyRequestRepository partyRequestRepository;

  @Autowired
  PartyNotificationEventProducer notificationProducer;

  @Autowired
  private DozerBeanMapper mapper;

  @Transactional
  public void createParty(String requestId) {
    Optional<PartyRequestEntity> request = partyRequestRepository.findById(requestId);
    if (request.isPresent()) {
      PartyRequestEntity partyRequest = request.get();      
      PartyEntity party = mapper.map(partyRequest, PartyEntity.class);
      partyRepository.save(party);
      partyRequest.setStatus(RequestStatus.SUCCESS);
      partyRequestRepository.save(partyRequest);
      notificationProducer.generateNotificationEvent(partyRequest);
    }
  }

  @Transactional(readOnly = true)
  public List<PartyRequestVO> getAllParties() {
    return partyRepository.findAll().stream().map(party -> mapper.map(party, PartyRequestVO.class))
        .collect(Collectors.toList());
  }
}
