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
public class GenerateQrRequest {

    @NotBlank
    private String partnerReferenceNo;

    private String merchantId;

    @JsonProperty("amount")
    @Valid
    private AmountRequest amountRequest;

}
