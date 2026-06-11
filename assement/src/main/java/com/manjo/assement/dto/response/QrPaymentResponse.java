package com.manjo.assement.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QrPaymentResponse {

    private String referenceNo;
    private String amount;
    private String status;
}