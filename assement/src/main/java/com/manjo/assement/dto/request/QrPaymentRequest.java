package com.manjo.assement.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QrPaymentRequest {

    @NotBlank
    private String referenceNo;

    @Valid
    @JsonProperty("amount")
    private AmountRequest amountRequest;

    @NotBlank
    private String status;
}