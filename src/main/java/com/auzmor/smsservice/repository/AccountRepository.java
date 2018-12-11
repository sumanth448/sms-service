package com.auzmor.smsservice.repository;

import com.auzmor.smsservice.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long>{
    Optional<Account> findByUserNameAndAuthId(String userName, String authId);
}
