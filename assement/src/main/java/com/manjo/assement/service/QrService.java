package com.manjo.assement.service;


import com.manjo.assement.dto.response.GenerateQrResponse;
import com.manjo.assement.dto.response.QrPaymentResponse;

import java.math.BigDecimal;

public interface QrService {

    GenerateQrResponse generateQr(
            String merchantId,
            String partnerReferenceNo,
            BigDecimal amount
    );

    QrPaymentResponse processPayment(
            String referenceNo,
            BigDecimal amount,
            String status
    );
}
