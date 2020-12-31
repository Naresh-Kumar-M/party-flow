package com.example.party.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.dozer.DozerBeanMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.party.controller.dto.PartyRequestDTO;
import com.example.party.controller.dto.PartyResponseDTO;
import com.example.party.service.PartyRequestService;
import com.example.party.service.PartyService;
import com.example.party.service.vo.PartyRequestVO;

@RestController
public class PartyController {

  private final PartyRequestService partyRequestService;
  private final PartyService partyService;
  private final DozerBeanMapper mapper;

  public PartyController(final PartyRequestService partyRequestService,
      final PartyService partyService, final DozerBeanMapper dozerBeanMapper) {
    this.partyRequestService = partyRequestService;
    this.partyService = partyService;
    this.mapper = dozerBeanMapper;
  }

  @PostMapping
  public PartyResponseDTO createPartyRequest(@RequestBody final PartyRequestDTO partyRequest) {
    PartyRequestVO partyRequestVO = mapper.map(partyRequest, PartyRequestVO.class);
    partyRequestVO = partyRequestService.createPartyRequest(partyRequestVO);
    return new PartyResponseDTO(partyRequestVO.getId());
  }

  @GetMapping("/requests")
  public List<PartyRequestDTO> getPartyRequests() {
    List<PartyRequestVO> partyRequests = partyRequestService.findAllRequests();
    return partyRequests.stream().map(partyReq -> mapper.map(partyReq, PartyRequestDTO.class))
        .collect(Collectors.toList());
  }

  @GetMapping
  public List<PartyRequestDTO> getPartys() {
    List<PartyRequestVO> partyRequests = partyService.getAllParties();
    return partyRequests.stream().map(partyReq -> mapper.map(partyReq, PartyRequestDTO.class))
        .collect(Collectors.toList());
  }

}
