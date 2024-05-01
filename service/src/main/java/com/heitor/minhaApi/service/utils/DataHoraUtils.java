package com.heitor.minhaApi.service.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class DataHoraUtils {

    public static LocalDateTime convertDataTimeFromTimeStampUTC(String timeStamp){

        Instant instant = Instant.ofEpochMilli(Long.parseLong(timeStamp));
        return LocalDateTime.ofInstant(instant, ZoneId.of("UTC"));
    }

    public static LocalDateTime convertDataTimeFromTimeStampAmericaSaoPaulo(String timeStamp){

        Instant instant = Instant.ofEpochMilli(Long.parseLong(timeStamp));
        ZoneId zoneId = ZoneId.of("America/Sao_Paulo");
        return LocalDateTime.ofInstant(instant, zoneId);
    }
}
