package com.example.party.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.party.repository.PartyRepository;
import com.example.party.repository.PartyRequestRepository;
import com.example.party.repository.entity.PartyEntity;
import com.example.party.repository.entity.PartyRequestEntity;
import com.example.party.repository.entity.RequestStatus;
import com.example.party.service.validation.AddressValidationEventProducer;
import com.example.party.service.vo.PartyRequestVO;

@Service
public class PartyRequestService {

  private final PartyRequestRepository partyRequestRepository;
  private final PartyRepository partyRepository;
  private final AddressValidationEventProducer addressValidationService;
  private final DozerBeanMapper mapper;

  public PartyRequestService(final PartyRequestRepository partyRequestRepository,
      final PartyRepository partyRepository,
      final AddressValidationEventProducer addressValidationService, final DozerBeanMapper mapper) {
    this.partyRequestRepository = partyRequestRepository;
    this.partyRepository = partyRepository;
    this.addressValidationService = addressValidationService;
    this.mapper = mapper;
  }

  @Transactional
  public PartyRequestVO createPartyRequest(final PartyRequestVO party) {
    PartyRequestEntity partyEntity = mapper.map(party, PartyRequestEntity.class);
    partyEntity.setStatus(RequestStatus.INITIATED);
    partyEntity = partyRequestRepository.save(partyEntity);
    party.setId(partyEntity.getId());
    addressValidationService.generateValidationEvent(party.getId(), party.getAddress());
    return party;
  }

  @Transactional
  public void processAddressValidatedPartyRequest(final String requestId) {
    Optional<PartyRequestEntity> request = partyRequestRepository.findById(requestId);
    if (request.isEmpty()) {
      return;
    }
    PartyRequestEntity partyRequest = request.get();
    PartyEntity party = partyRepository.findByEmail(partyRequest.getEmail());
    if (null != party) {
      partyRequest.setStatus(RequestStatus.DUPLICATE_REQUEST);
      return;
    }
    partyRequest.setStatus(RequestStatus.AWAITING_APPROVAL);
  }

  @Transactional(readOnly = true)
  public List<PartyRequestVO> findAllRequests() {
    return partyRequestRepository.findAll().stream()
        .map(request -> mapper.map(request, PartyRequestVO.class)).collect(Collectors.toList());
  }

}
