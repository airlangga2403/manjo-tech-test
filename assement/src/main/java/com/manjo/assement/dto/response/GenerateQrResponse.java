package com.manjo.assement.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenerateQrResponse {
    private String referenceNo;

    private String partnerReferenceNo;

    private String qrContent;
}
