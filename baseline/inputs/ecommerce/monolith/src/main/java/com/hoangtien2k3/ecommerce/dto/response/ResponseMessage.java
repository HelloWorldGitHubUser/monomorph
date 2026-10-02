package com.hoangtien2k3.ecommerce.dto.response;

import lombok.*;

import jakarta.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ResponseMessage {
    @Size(min = 10, max = 500)
    private String message;
}
