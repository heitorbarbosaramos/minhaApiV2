package com.heitor.minhaApi.feign;

import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "whatsAppCliente", url = "${api.whatsapp.url}")
public interface WhatsAppCliente {

    @RequestMapping(method = RequestMethod.GET, path = "/session/start/{session}")
    WhatsAppSessionResponse createSession(@RequestHeader("x-api-key")String header, @PathVariable("session") String session);

    @RequestMapping(method = RequestMethod.GET, path = "/session/status/{session}")
    WhatsAppSessionResponse recoveryStatusSession(@RequestHeader("x-api-key")String header, @PathVariable("session") String session);

    @RequestMapping(method = RequestMethod.GET, path = "/session/qr/{session}")
    WhatsAppSessionResponse recoveryCodeQr(@RequestHeader("x-api-key")String header, @PathVariable("session") String session);

    @RequestMapping(method = RequestMethod.GET, path = "/session/qr/{session}/image")
    Response qrCodeWhatsAppSession(@RequestHeader("x-api-key")String header, @PathVariable("session") String session);

    @RequestMapping(method = RequestMethod.POST, path = "/client/sendMessage/{session}")
    SendMessageResponse sendMessage(@RequestHeader("x-api-key")String header, @PathVariable("session") String session, @RequestBody SendMessageRequest request);
}
