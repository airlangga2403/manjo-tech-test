package com.manjo.assement.service;

import com.manjo.assement.dto.request.PaymentNotificationRequest;
import com.manjo.assement.dto.response.PaymentNotificationResponse;
import com.manjo.assement.dto.response.PaymentStatusResponse;
import com.manjo.assement.dto.response.TransactionTrackerResponse;
import com.manjo.assement.entity.Transaction;
import com.manjo.assement.exception.BadRequestException;
import com.manjo.assement.exception.TransactionNotFoundException;
import com.manjo.assement.repository.TransactionRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {
    private final TransactionRepository transactionRepository;

    public PaymentServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public PaymentNotificationResponse processNotification(
            PaymentNotificationRequest request) {
        Transaction trx =
                transactionRepository
                        .findByReferenceNumber(request.getOriginalReferenceNo())
                        .orElseThrow(()
                                -> new TransactionNotFoundException(
                                "Transaction Not Found : "
                                        + request.getOriginalReferenceNo()));

        if (!trx.getPartnerReferenceNumber().equals(
                request.getOriginalPartnerReferenceNo())) {
            throw new BadRequestException("Partner Reference Number Mismatch");
        }

        if (trx.getAmount().compareTo(request.getAmountRequest().toBigDecimal())
                != 0) {
            throw new BadRequestException("Amount Mismatch");
        }

        log.info("Payment callback validated successfully. referenceNo={}",
                trx.getReferenceNumber());

        return PaymentNotificationResponse.builder()
                .transactionStatusDesc(trx.getStatus().name())
                .build();
    }

    @Override
    public PaymentStatusResponse getStatus(String referenceNo) {
        Transaction trx = transactionRepository.findByReferenceNumber(referenceNo)
                .orElseThrow(()
                        -> new TransactionNotFoundException(
                        "Transaction Not Found"));

        return PaymentStatusResponse.builder()
                .referenceNo(trx.getReferenceNumber())
                .merchantId(trx.getMerchantId())
                .amount(trx.getAmount().toString())
                .status(trx.getStatus().name())
                .transactionDate(trx.getTransactionDate().toString())
                .paidDate(
                        trx.getPaidDate() == null ? null : trx.getPaidDate().toString())
                .build();
    }

    @Override
    public List<TransactionTrackerResponse> getAllTransactions() {
        return transactionRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<TransactionTrackerResponse> searchTransactions(
            String merchantId, String trxId, String partnerReferenceNumber,
            String referenceNumber, String status

    ) {
        return transactionRepository.findAll()
                .stream()
                .filter(t
                        -> merchantId == null || merchantId.isBlank()
                        || t.getMerchantId().contains(merchantId))
                .filter(t
                        -> trxId == null || trxId.isBlank() || t.getTrxId().contains(trxId))
                .filter(t
                        -> partnerReferenceNumber == null
                        || partnerReferenceNumber.isBlank()
                        || t.getPartnerReferenceNumber().contains(
                        partnerReferenceNumber))
                .filter(t
                        -> referenceNumber == null || referenceNumber.isBlank()
                        || t.getReferenceNumber().contains(referenceNumber))
                .filter(t
                        -> status == null || status.isBlank()
                        || t.getStatus().name().equalsIgnoreCase(status))
                .map(this::mapToResponse)

                .toList();
    }

    private TransactionTrackerResponse mapToResponse(Transaction transaction) {
        return TransactionTrackerResponse.builder()
                .merchantId(transaction.getMerchantId())
                .trxId(transaction.getTrxId())
                .partnerReferenceNumber(transaction.getPartnerReferenceNumber())
                .referenceNumber(transaction.getReferenceNumber())
                .amount(transaction.getAmount().toString())
                .status(transaction.getStatus().name())
                .transactionDate(transaction.getTransactionDate().toString())
                .paidDate(transaction.getPaidDate() == null
                        ? null
                        : transaction.getPaidDate().toString())
                .build();
    }
}