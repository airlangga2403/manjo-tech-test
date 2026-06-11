package com.manjo.assement.util;

import java.util.UUID;

public class Util {

    public static String genreateReferenceNumber() {
        return "A" + UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 12)
                .toUpperCase();
    }

    public static String generateDummyQr(String referenceNo) {
        return "DUMMY_QR_" + referenceNo;
    }
}