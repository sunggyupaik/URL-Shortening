package com.spring.melon.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@NoArgsConstructor
public class ErrorResponse {
    private int status;
    private String message;
    private String reason;

    @Builder
    public ErrorResponse(int status, String message, String reason){
        this.status=status;
        this.message=message;
        this.reason=reason;
    }
}
