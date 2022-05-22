package com.sber.practika.service.transactionService;

import com.sber.practika.entity.TransactionTransfer;
import com.sber.practika.entity.statuses.StatusTransaction;
import com.sber.practika.repo.TransactionTransferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionTransferRepository transactionTransferRepository;

    public List<TransactionTransfer> allTransactionsByBankNumber(String bankNumber) {
        return wrapperFilterByStatus(transactionTransferRepository.findAllByBankNumber(bankNumber), StatusTransaction.ACCESS);
    }

    public List<TransactionTransfer> allTransactionsByBankCard(BigInteger bankCard) {
        return wrapperFilterByStatus(transactionTransferRepository.findAllByBankCard(bankCard), StatusTransaction.ACCESS);
    }

    private List<TransactionTransfer> wrapperFilterByStatus(List<TransactionTransfer> list, StatusTransaction statusTransaction){
        return list.stream()
                .filter(transactionTransfer -> transactionTransfer.getStatusTransaction() == statusTransaction.getCode())
                .collect(Collectors.toList());
    }
}
