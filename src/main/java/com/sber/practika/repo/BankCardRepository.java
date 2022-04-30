package com.sber.practika.repo;

import com.sber.practika.entity.BankCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.Optional;

public interface BankCardRepository extends JpaRepository<BankCard, BigInteger> {
    Optional<BankCard> findById(BigInteger id);
}
