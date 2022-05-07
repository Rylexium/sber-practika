package com.sber.practika.service.transactionService;

import com.sber.practika.entity.TransactionTransfer;
import com.sber.practika.repo.TransactionTransferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionTransferRepository transactionTransferRepository;

    public List<TransactionTransfer> allTransactionsByBankNumber(String bankNumber) {
        return transactionTransferRepository.findAllByBankNumber(bankNumber);
    }

    public List<TransactionTransfer> allTransactionsByBankNumber(BigInteger bankCard) {
        return transactionTransferRepository.findAllByBankCard(bankCard);
    }
}
