package com.manjo.assement.service;

import com.manjo.assement.dto.response.GenerateQrResponse;
import com.manjo.assement.dto.response.QrPaymentResponse;
import com.manjo.assement.entity.Transaction;
import com.manjo.assement.entity.TransactionStatus;
import com.manjo.assement.exception.BadRequestException;
import com.manjo.assement.exception.TransactionNotFoundException;
import com.manjo.assement.repository.TransactionRepository;
import com.manjo.assement.util.Util;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class QrServiceImpl implements QrService {
    private final TransactionRepository transactionRepository;

    public QrServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    @Transactional
    public GenerateQrResponse generateQr(
            String merchantId, String partnerReferenceNo, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Amount must be greater than zero");
        }

        String referenceNo = Util.genreateReferenceNumber();
        String trxId = UUID.randomUUID().toString();

        Transaction transaction = Transaction.builder()
                .merchantId(merchantId)
                .amount(amount)
                .trxId(trxId)
                .partnerReferenceNumber(partnerReferenceNo)
                .referenceNumber(referenceNo)
                .status(TransactionStatus.PENDING)
                .transactionDate(LocalDateTime.now())
                .build();

        transactionRepository.save(transaction);

        return GenerateQrResponse.builder()
                .referenceNo(referenceNo)
                .partnerReferenceNo(partnerReferenceNo)
                .qrContent(Util.generateDummyQr(referenceNo))
                .build();
    }

    @Override
    @Transactional
    public QrPaymentResponse processPayment(
            String referenceNo, BigDecimal amount, String status) {
        Transaction trx = transactionRepository.findByReferenceNumber(referenceNo)
                .orElseThrow(()
                        -> new TransactionNotFoundException(
                        "Transaction Not Found"));

        if (trx.getStatus() != TransactionStatus.PENDING) { // AVOID DUPLICATE PROCESS.
            throw new BadRequestException(
                    "Transaction already processed"
            );
        }

        trx.setStatus(TransactionStatus.valueOf(status.toUpperCase()));

        trx.setPaidDate(LocalDateTime.now());

        transactionRepository.save(trx);

        return QrPaymentResponse.builder()
                .referenceNo(trx.getReferenceNumber())
                .amount(trx.getAmount().toString())
                .status(trx.getStatus().name())
                .build();
    }
}