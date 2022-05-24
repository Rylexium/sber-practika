package com.sber.practika.service.finalize;

import com.sber.practika.entity.BankCard;
import com.sber.practika.entity.Users;
import com.sber.practika.service.transfer.util.Searcher;
import com.sber.practika.service.transfer.util.TransferComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FinalizeTransferService {
    private final Searcher searcher;
    private final TransferComponent transferComponent;

    public void bankCardToBankNumber(Long bankCard, String bankNumber, Long value, UUID uuidTransaction) {
        BankCard card = searcher.searchBankCard(bankCard, uuidTransaction);
        Users user = searcher.searchBankNumber(bankNumber, uuidTransaction);

        transferComponent.transferBankCardToBankNumber(card, user, value, uuidTransaction);
    }

    public void bankCardToBankCard(Long bankCard1, Long bankCard2, Long value, UUID uuidTransaction) {
        BankCard card1 = searcher.searchBankCard(bankCard1, uuidTransaction);
        BankCard card2 = searcher.searchBankCard(bankCard2, uuidTransaction);

        transferComponent.transferBankCardToBankCard(card1, card2, value, uuidTransaction);
    }

    public void bankNumberToBankNumber(String bankNumber1, String bankNumber2, Long value, UUID uuidTransaction) {
        Users user1 = searcher.searchBankNumber(bankNumber1, uuidTransaction);
        Users user2 = searcher.searchBankNumber(bankNumber2, uuidTransaction);

        transferComponent.transferBankNumberToBankNumber(user1, user2, value, uuidTransaction);
    }

    public void bankNumberToBankCard(String bankNumber, Long bankCard, Long value, UUID uuidTransaction) {
        BankCard card = searcher.searchBankCard(bankCard, uuidTransaction);
        Users user = searcher.searchBankNumber(bankNumber, uuidTransaction);

        transferComponent.transferBankNumberToBankCard(user, card, value, uuidTransaction);
    }
}
