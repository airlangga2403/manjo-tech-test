package com.manjo.assement.dto.request;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AmountRequest {

    private String value;

    private String currency;

    public BigDecimal toBigDecimal() {
        return new BigDecimal(value);
    }
}