package com.auzmor.smsservice.repository;

import com.auzmor.smsservice.models.PhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PhoneRepository extends JpaRepository<PhoneNumber,Long> {
    Optional<PhoneNumber> findByNumberAndAccountId(String number, Long id);
}
