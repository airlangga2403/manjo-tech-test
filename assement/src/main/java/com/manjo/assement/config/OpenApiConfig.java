package com.manjo.assement.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.*;


@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI paymentGatewayApi() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title(
                                        "Manjo Payment Gateway API"
                                )
                                .version(
                                        "1.0.0"
                                )
                                .description(
                                        "Technical Test PT Manjo Teknologi Indonesia"
                                )
                );
    }
}
