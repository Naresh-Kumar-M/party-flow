package com.example.party.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.party.repository.entity.PartyEntity;

@Repository
public interface PartyRepository extends JpaRepository<PartyEntity, String> {

  @Query("FROM PartyEntity p where p.contactNumber = :contact")
  PartyEntity findByContact(@Param("contact") String contact);

  @Query("FROM PartyEntity p where p.email = :email")
  PartyEntity findByEmail(@Param("email") String email);
}
