package com.spring.melon.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SuccessResponse {
    private int status;
    private String message;
    private String url;

    @Builder
    public SuccessResponse(int status, String message, String url){
        this.status=status;
        this.message=message;
        this.url=url;
    }
}
