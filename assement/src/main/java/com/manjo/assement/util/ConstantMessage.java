package com.manjo.assement.util;

public class ConstantMessage {
    public static final String SUCCESS_MESSAGE = "Successful";
    public static final String ERROR_MESSAGE = "ERROR";
    public static final String IDR_CURRENCY = "IDR";

    public static final String VALIDATION_MESSAGE_CURRENCY_IDR = "Currency must be IDR";
    public static final String VALIDATION_MESSAGE_INVALIDATION_SIGNATURE = "Invalid Signature";

    // --- Added Response Codes ---
    public static final String RESPONSE_CODE_SUCCESS = "200";

    public static final String RESPONSE_CODE_PAYMENT_NOTIFICATION = "2005100";
    public static final String RESPONSE_CODE_PAYMENT_STATUS = "2005200";
    public static final String RESPONSE_CODE_QR_GENERATE = "2004700";
    public static final String RESPONSE_CODE_QR_PAYMENT = "2004800";
}