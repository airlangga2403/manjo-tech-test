package com.manjo.assement.service;

import com.manjo.assement.dto.request.PaymentNotificationRequest;
import com.manjo.assement.dto.response.PaymentNotificationResponse;
import com.manjo.assement.dto.response.PaymentStatusResponse;
import com.manjo.assement.dto.response.TransactionTrackerResponse;

import java.util.List;


public interface PaymentService {
    PaymentNotificationResponse processNotification(
            PaymentNotificationRequest request
    );

    PaymentStatusResponse getStatus(
            String referenceNo
    );

    List<TransactionTrackerResponse> getAllTransactions();

    List<TransactionTrackerResponse> searchTransactions(
            String merchantId,
            String trxId,
            String partnerReferenceNumber,
            String referenceNumber,
            String status
    );
}
