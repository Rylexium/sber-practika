package com.sber.practika.repo;

import com.sber.practika.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionTransferRepository extends JpaRepository<Transaction, UUID> {
}
