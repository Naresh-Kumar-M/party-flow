package com.example.party.service.event;

public class AddressValidationRequestEvent extends Event {

  private String requestId;
  private String address;

  public AddressValidationRequestEvent() {}

  public AddressValidationRequestEvent(String requestId, String address) {
    super();
    this.requestId = requestId;
    this.address = address;
  }

  public void setRequestId(String requestId) {
    this.requestId = requestId;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getRequestId() {
    return requestId;
  }

  public String getAddress() {
    return address;
  }

}

