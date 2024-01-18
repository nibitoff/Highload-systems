package com.alsab.boozycalc.notification.controller;

import com.alsab.boozycalc.notification.dto.SaleNotificationDto;
import com.alsab.boozycalc.notification.service.WebSocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WebSocketController {

    private final WebSocketService service;

//    @PostMapping("/send-message")
//    public void sendMessage(@RequestBody final Message message) {
//        service.notifyFrontend(message.getContent());
//    }
//
//    @PostMapping("/send-private-message/{id}")
//    public void sendPrivateMessage(@PathVariable final String id,
//                                   @RequestBody final Message message) {
//        service.notifyUser(id, message.getContent());
//    }
    @PostMapping("/start-sale")
    public void startSale(@RequestBody SaleNotificationDto sale) {
        service.notifySaleStart(sale);
    }
}
