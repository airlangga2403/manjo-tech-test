package com.manjo.assement.validator;

import com.manjo.assement.exception.BadRequestException;
import com.manjo.assement.exception.UnauthorizedException;
import com.manjo.assement.security.HmacUtil;
import com.manjo.assement.util.ConstantMessage;
import org.springframework.stereotype.Component;

@Component
public class RequestValidator {

    private final HmacUtil hmacUtil;

    public RequestValidator(HmacUtil hmacUtil) {
        this.hmacUtil = hmacUtil;
    }

    public void validateSignature(String rawBody, String signature) {
        if (signature == null || signature.isBlank()) {
            throw new UnauthorizedException("X-SIGNATURE header is required");
        }
        if (!hmacUtil.verifySignature(rawBody, signature)) {
            throw new UnauthorizedException(ConstantMessage.VALIDATION_MESSAGE_INVALIDATION_SIGNATURE);
        }
    }

    public void validateIdrCurrency(String currency) {
        if (currency == null || currency.isBlank()) {
            throw new BadRequestException("Amount and currency are required");
        }
        if (!ConstantMessage.IDR_CURRENCY.equalsIgnoreCase(currency)) {
            throw new BadRequestException(ConstantMessage.VALIDATION_MESSAGE_CURRENCY_IDR);
        }
    }
}