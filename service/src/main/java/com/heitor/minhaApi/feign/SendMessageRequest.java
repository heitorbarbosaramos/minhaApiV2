package com.heitor.minhaApi.feign;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendMessageRequest {

    private String chatId;
    private String contentType;
    private String content;
}
