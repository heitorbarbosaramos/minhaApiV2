package com.heitor.minhaApi.security.feignClient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.heitor.minhaApi.service.utils.DataHoraUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashMap;

@Getter
@Setter
@AllArgsConstructor
public class UserSessionRepresentation {

    private String id;
    private String username;
    private String userId;
    private String ipAddress;
    private String start;
    private String lastAccess;
    private HashMap<String, String > clients = new HashMap<>();


    @JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    public LocalDateTime getStartFormat(){
        return DataHoraUtils.convertDataTimeFromTimeStampAmericaSaoPaulo(this.start);
    }

    @JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    public LocalDateTime getLastAccessFormat(){
        return DataHoraUtils.convertDataTimeFromTimeStampAmericaSaoPaulo(this.lastAccess);
    }
}
