package com.manjo.assement.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentStatusResponse {

    private String referenceNo;

    private String merchantId;

    private String amount;

    private String status;

    private String transactionDate;

    private String paidDate;
}
