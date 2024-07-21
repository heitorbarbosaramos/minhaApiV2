package com.heitor.minhaApi.feign;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WhatsAppSessionResponse {

    private String success;
    private String message;
    private String error;
    private String state;
    private String qr;
}
