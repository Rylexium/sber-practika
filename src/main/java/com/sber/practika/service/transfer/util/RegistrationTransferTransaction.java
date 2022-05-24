package com.sber.practika.service.transfer.util;

import com.sber.practika.entity.TransactionTransfer;
import com.sber.practika.entity.statuses.StatusTransaction;
import com.sber.practika.repo.TransactionTransferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RegistrationTransferTransaction {
    private final TransactionTransferRepository transactionTransferRepository;
    public UUID registration(String senderBankNumber, String recipientBankNumber,
                                    Long senderBankCard, Long recipientBankCard,
                                    Long value) {
        TransactionTransfer transactionTransfer = new TransactionTransfer(
                senderBankNumber, recipientBankNumber,
                senderBankCard,   recipientBankCard,
                value, StatusTransaction.IN_PROGRESS.getCode());
        transactionTransferRepository.save(transactionTransfer);

        return transactionTransfer.getUuid();
    }

    public void access(UUID uuidTransaction) {
        transactionTransferRepository.save(
                findTransactionAndSetStatus(uuidTransaction, StatusTransaction.ACCESS));
    }
    public void cancel(UUID uuidTransaction) {
        transactionTransferRepository.save(
                findTransactionAndSetStatus(uuidTransaction, StatusTransaction.CANCEL));
    }

    public void confirmationWait(UUID uuidTransaction) {
        transactionTransferRepository.save(
                findTransactionAndSetStatus(uuidTransaction, StatusTransaction.CONFIRMATION_WAIT));
    }
    public void confirmationFailed(UUID uuidTransaction) {
        transactionTransferRepository.save(
                findTransactionAndSetStatus(uuidTransaction, StatusTransaction.CONFIRMATION_FAILED));
    }
    public void confirmationAccess(UUID uuidTransaction) {
        transactionTransferRepository.save(
                findTransactionAndSetStatus(uuidTransaction, StatusTransaction.CONFIRMATION_ACCESS));
    }
    public void cannotBePerformed(UUID uuidTransaction) {
        transactionTransferRepository.save(
                findTransactionAndSetStatus(uuidTransaction, StatusTransaction.CANNOT_BE_PERFORMED));
    }
    public void cannotBePerformedDueCard(UUID uuidTransaction) {
        transactionTransferRepository.save(
                findTransactionAndSetStatus(uuidTransaction, StatusTransaction.CANNOT_BE_PERFORMED_DUE_CARD));
    }
    public void cannotBePerformedDueUser(UUID uuidTransaction) {
        transactionTransferRepository.save(
                findTransactionAndSetStatus(uuidTransaction, StatusTransaction.CANNOT_BE_PERFORMED_DUE_USER));
    }

    public void noMoney(UUID uuidTransaction) {
        transactionTransferRepository.save(
                findTransactionAndSetStatus(uuidTransaction, StatusTransaction.NO_MONEY));
    }

    private TransactionTransfer findTransactionAndSetStatus(UUID uuidTransaction, StatusTransaction statusTransaction) {
        TransactionTransfer transactionTransfer = transactionTransferRepository.getById(uuidTransaction);
        transactionTransfer.setStatusTransaction(statusTransaction.getCode());
        return transactionTransfer;
    }
}
