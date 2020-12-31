package com.example.party.repository.entity;

public enum RequestStatus {
  INITIATED, 
  ADDRESS_VALIDATED, 
  DUPLICATE_REQUEST, 
  AWAITING_APPROVAL, 
  APPROVAL_SUCCESS, 
  APPROVAL_FAILED, 
  SUCCESS,
  FAILED
}
