package com.example.party.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PartyResponseDTO {

  private final String requestId;

  public PartyResponseDTO(String requestId) {
    super();
    this.requestId = requestId;
  }

  public String getRequestId() {
    return requestId;
  }

}
