package com.manjo.assement.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class TransactionTrackerResponse {

    private String merchantId;

    private String trxId;

    private String partnerReferenceNumber;

    private String referenceNumber;

    private String amount;

    private String status;

    private String transactionDate;

    private String paidDate;
}