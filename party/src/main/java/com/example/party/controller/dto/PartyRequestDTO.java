package com.example.party.controller.dto;

import org.springframework.lang.NonNull;
import com.example.party.repository.entity.RequestStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PartyRequestDTO {

  private String id;
  @NonNull
  private String firstName;
  @NonNull
  private String lastName;
  @NonNull
  private String address;
  @NonNull
  private String email;
  @NonNull
  private String contactNumber;
  private RequestStatus status;

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getContactNumber() {
    return contactNumber;
  }

  public void setContactNumber(String contactNumber) {
    this.contactNumber = contactNumber;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public RequestStatus getStatus() {
    return status;
  }

  public void setStatus(RequestStatus status) {
    this.status = status;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

}
