package com.heitor.minhaApi.feign;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendMessageResponse {

    private Boolean success;
    private SendMessageResponseReturn message;
}
