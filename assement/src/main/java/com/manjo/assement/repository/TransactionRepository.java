package com.manjo.assement.repository;


import com.manjo.assement.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    Optional<Transaction> findByReferenceNumber(String referenceNumber);

}
