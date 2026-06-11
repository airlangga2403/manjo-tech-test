package com.manjo.assement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.manjo.assement.dto.common.ApiResponse;
import com.manjo.assement.dto.request.GenerateQrRequest;
import com.manjo.assement.dto.request.QrPaymentRequest;
import com.manjo.assement.dto.response.GenerateQrResponse;
import com.manjo.assement.dto.response.QrPaymentResponse;
import com.manjo.assement.exception.BadRequestException;
import com.manjo.assement.service.QrService;
import com.manjo.assement.util.ConstantMessage;
import com.manjo.assement.validator.RequestValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/qr")
@Slf4j
@Tag(name = "QR Management",
        description =
                "Endpoints for generating QR codes and processing QR payments")
public class QrController {
    private final QrService qrService;
    private final ObjectMapper objectMapper;
    private final RequestValidator requestValidator;

    public QrController(QrService qrService, ObjectMapper objectMapper,
                        RequestValidator requestValidator) {
        this.qrService = qrService;
        this.objectMapper = objectMapper;
        this.requestValidator = requestValidator;
    }

    @Operation(summary = "Generate QR Code",
            description =
                    "Generates a standard QR code for a merchant with IDR currency.")
    @ApiResponses(value =
            {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200", description = "QR generated successfully",
                            content =
                            @Content(schema = @Schema(implementation = ApiResponse.class)))
                    ,
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "400",
                            description = "Invalid request or currency",
                            content = @Content),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "401",
                            description = "Invalid or missing X-SIGNATURE",
                            content = @Content)
            })
    @PostMapping("/generate")
    public ApiResponse<GenerateQrResponse>
    generateQr(@RequestHeader(value = "X-SIGNATURE") String signature,
               @RequestBody String rawBody) throws Exception {
        log.info(
                "Received request on /api/v1/qr/generate | Signature: {} | RawBody: {}",
                signature, rawBody);

        requestValidator.validateSignature(rawBody, signature);

        GenerateQrRequest request =
                objectMapper.readValue(rawBody, GenerateQrRequest.class);

        if (request.getAmountRequest() == null) {
            throw new BadRequestException("Amount is required");
        }

        requestValidator.validateIdrCurrency(
                request.getAmountRequest().getCurrency());

        GenerateQrResponse response = qrService.generateQr(request.getMerchantId(),
                request.getPartnerReferenceNo(),
                new BigDecimal(request.getAmountRequest().getValue()));

        log.info("Successfully generated QR for PartnerReferenceNo: {}",
                request.getPartnerReferenceNo());

        return ApiResponse.<GenerateQrResponse>builder()
                .responseCode(ConstantMessage.RESPONSE_CODE_QR_GENERATE)
                .responseMessage(ConstantMessage.SUCCESS_MESSAGE)
                .data(response)
                .build();
    }

    @Operation(summary = "Process QR Payment",
            description =
                    "Processes a payment transaction using a scanned QR code reference.")
    @ApiResponses(value =
            {
                    @io.swagger.v3.oas.annotations.responses.
                            ApiResponse(responseCode = "200",
                            description = "Payment processed successfully",
                            content =
                            @Content(schema = @Schema(implementation = ApiResponse.class)))
                    ,
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "400",
                            description = "Invalid request or currency",
                            content = @Content),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "401",
                            description = "Invalid or missing X-SIGNATURE",
                            content = @Content)
            })
    @PostMapping("/payment")
    public ApiResponse<QrPaymentResponse>
    payment(@RequestHeader(value = "X-SIGNATURE") String signature,
            @RequestBody String rawBody) throws Exception {
        log.info(
                "Received request on /api/v1/qr/payment | Signature: {} | RawBody: {}",
                signature, rawBody);

        requestValidator.validateSignature(rawBody, signature);

        QrPaymentRequest request =
                objectMapper.readValue(rawBody, QrPaymentRequest.class);

        if (request.getAmountRequest() == null) {
            throw new BadRequestException("Amount is required");
        }

        requestValidator.validateIdrCurrency(
                request.getAmountRequest().getCurrency());

        QrPaymentResponse response =
                qrService.processPayment(request.getReferenceNo(),
                        new BigDecimal(request.getAmountRequest().getValue()),
                        request.getStatus());

        log.info("Successfully processed QR payment for ReferenceNo: {}",
                request.getReferenceNo());

        return ApiResponse.<QrPaymentResponse>builder()
                .responseCode(ConstantMessage.RESPONSE_CODE_QR_PAYMENT)
                .responseMessage(ConstantMessage.SUCCESS_MESSAGE)
                .data(response)
                .build();
    }
}