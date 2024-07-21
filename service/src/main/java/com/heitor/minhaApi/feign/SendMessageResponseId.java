package com.heitor.minhaApi.feign;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendMessageResponseId {

    private Boolean fromMe;
    private String remote;
    private String id;
    private String self;
    @JsonProperty(value = "_serialized")
    private String serialized;
}
