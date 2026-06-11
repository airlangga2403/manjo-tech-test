package com.manjo.assement.dto.common;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private String responseCode;

    private String responseMessage;

    private LocalDateTime timestamp;
}
