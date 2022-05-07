package com.sber.practika.repo;

import com.sber.practika.entity.TransactionTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

public interface TransactionTransferRepository extends JpaRepository<TransactionTransfer, UUID> {
    @Query(value =
            "select * from transaction_transfer " +
                    "where :bank_number in (bank_number1, bank_number2)", nativeQuery = true)
    List<TransactionTransfer> findAllByBankNumber(String bank_number);

    @Query(value =
            "select * from transaction_transfer" +
                    " where 5234234343124321 in (bank_card1, bank_card2)", nativeQuery = true)
    List<TransactionTransfer> findAllByBankCard(BigInteger bank_card);
}
