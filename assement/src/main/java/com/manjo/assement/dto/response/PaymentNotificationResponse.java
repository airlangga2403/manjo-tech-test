package com.manjo.assement.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentNotificationResponse {

    private String transactionStatusDesc;

}
