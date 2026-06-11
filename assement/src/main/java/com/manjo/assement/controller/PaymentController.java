package com.manjo.assement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.manjo.assement.dto.common.ApiResponse;
import com.manjo.assement.dto.request.PaymentNotificationRequest;
import com.manjo.assement.dto.response.PaymentNotificationResponse;
import com.manjo.assement.dto.response.PaymentStatusResponse;
import com.manjo.assement.dto.response.TransactionTrackerResponse;
import com.manjo.assement.exception.UnauthorizedException;
import com.manjo.assement.security.HmacUtil;
import com.manjo.assement.service.PaymentService;
import com.manjo.assement.util.ConstantMessage;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payment")
@Slf4j
public class PaymentController {
    private final PaymentService paymentService;
    private final HmacUtil hmacUtil;
    private final ObjectMapper objectMapper;

    public PaymentController(PaymentService paymentService, HmacUtil hmacUtil,
                             ObjectMapper objectMapper) {
        this.paymentService = paymentService;
        this.hmacUtil = hmacUtil;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/notification")
    public ApiResponse<PaymentNotificationResponse> paymentNotification(
            @RequestHeader("X-SIGNATURE") String signature,
            @RequestBody String rawBody

    ) throws Exception {
        log.info("Received request on /api/v1/payment/notification | Signature: {} "
                        + "| RawBody: {}",
                signature, rawBody);

        if (!hmacUtil.verifySignature(rawBody, signature)) {
            throw new UnauthorizedException(
                    ConstantMessage.VALIDATION_MESSAGE_INVALIDATION_SIGNATURE);
        }

        PaymentNotificationRequest request =
                objectMapper.readValue(rawBody, PaymentNotificationRequest.class);

        PaymentNotificationResponse response =
                paymentService.processNotification(request);

        log.info("Successfully processed payment notification");

        return ApiResponse.<PaymentNotificationResponse>builder()
                .responseCode(ConstantMessage.RESPONSE_CODE_PAYMENT_NOTIFICATION)
                .responseMessage(ConstantMessage.SUCCESS_MESSAGE)
                .data(response)
                .build();
    }

    // ========================== //

    // UNTUK REACT MERHCHANT TRACKER ONLY
    @GetMapping("/status/{referenceNo}")
    public ApiResponse<PaymentStatusResponse> getStatus(
            @PathVariable String referenceNo) {
        return ApiResponse.<PaymentStatusResponse>builder()
                .responseCode(ConstantMessage.RESPONSE_CODE_PAYMENT_STATUS)
                .responseMessage(ConstantMessage.SUCCESS_MESSAGE)
                .data(paymentService.getStatus(referenceNo))
                .build();
    }

    @GetMapping("/transactions")
    public ApiResponse<List<TransactionTrackerResponse>> getAllTransactions() {
        return ApiResponse.<List<TransactionTrackerResponse>>builder()
                .responseCode(ConstantMessage.RESPONSE_CODE_PAYMENT_STATUS)
                .responseMessage(ConstantMessage.SUCCESS_MESSAGE)
                .data(paymentService.getAllTransactions())
                .build();
    }

    @GetMapping("/transactions/search")
    public ApiResponse<List<TransactionTrackerResponse>> searchTransactions(
            @RequestParam(required = false) String merchantId,
            @RequestParam(required = false) String trxId,
            @RequestParam(required = false) String partnerReferenceNumber,
            @RequestParam(required = false) String referenceNumber,
            @RequestParam(required = false) String status

    ) {
        return ApiResponse.<List<TransactionTrackerResponse>>builder()
                .responseCode(ConstantMessage.RESPONSE_CODE_PAYMENT_STATUS)
                .responseMessage(ConstantMessage.SUCCESS_MESSAGE)
                .data(paymentService.searchTransactions(
                        merchantId, trxId, partnerReferenceNumber, referenceNumber, status))
                .build();
    }
}