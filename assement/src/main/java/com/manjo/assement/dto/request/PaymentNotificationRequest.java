package com.manjo.assement.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentNotificationRequest {

    @NotBlank
    private String originalReferenceNo;

    @NotBlank
    private String originalPartnerReferenceNo;

    @NotBlank
    private String paidTime;

    @Valid
    @JsonProperty("amount")
    private AmountRequest amountRequest;
}
