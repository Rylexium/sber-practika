package com.sber.practika.repo;

import com.sber.practika.entity.BankCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface BankCardRepository extends JpaRepository<BankCard, BigInteger> {
    Optional<BankCard> findById(Long id);

    @Query(value = "select * from bank_card where bank_number=:bank_number", nativeQuery = true)
    List<BankCard> findAllByBankNumber(@Param("bank_number") String bankNumber);
}
