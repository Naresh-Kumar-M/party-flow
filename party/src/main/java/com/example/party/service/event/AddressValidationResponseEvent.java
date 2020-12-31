package com.example.party.service.event;

public class AddressValidationResponseEvent extends Event {

  private boolean isValid;
  private String requestId;
  private String address;

  public boolean isValid() {
    return isValid;
  }

  public void setValid(boolean isValid) {
    this.isValid = isValid;
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
