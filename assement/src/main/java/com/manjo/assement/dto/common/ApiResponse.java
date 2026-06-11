package com.manjo.assement.dto.common;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse<T> {
    private String responseCode;

    private String responseMessage;

    private T data;
}
