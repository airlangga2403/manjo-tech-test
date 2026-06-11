package com.manjo.assement.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;


@Slf4j
@Component
public class HmacUtil {

    @Value("${app.security.secret-key}")
    private String secretKey;


    public String generateSignature(String payload){

        try {
            Mac mac = Mac.getInstance("HmacSHA256");

            SecretKeySpec secretKeySpec = new SecretKeySpec(
                    secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256"
            );

            mac.init(secretKeySpec);

            byte[] hash = mac.doFinal(payload.getBytes(StandardCharsets.UTF_8));

            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public boolean verifySignature(
            String payload,
            String incomingSignature
    ) {

        String generated =
                generateSignature(payload);

        log.debug(
                "Generated : " + generated
        );

        log.debug(
                "Incoming  : " + incomingSignature
        );

        return generated.equals(
                incomingSignature
        );
    }
}
