package com.heitor.minhaApi.feign;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendMessageResponseReturn {

    private Integer ack;
    private Boolean hasMedia;
    private String body;
    private String type;
    private Long timestamp;
    private String from;
    private String to;
    private String deviceType;
    private Boolean isForwarded;
    private Integer forwardingScore;
    private Boolean isStatus;
    private Boolean isStarred;
    private Boolean fromMe;
    private Boolean hasQuotedMsg;
    private Boolean hasReaction;
    private SendMessageResponseId id;
}
