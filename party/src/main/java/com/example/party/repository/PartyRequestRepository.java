package com.example.party.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.party.repository.entity.PartyRequestEntity;

@Repository
public interface PartyRequestRepository extends JpaRepository<PartyRequestEntity, String> {
  
}
