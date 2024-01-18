package com.alsab.boozycalc.notification.service;

import com.alsab.boozycalc.notification.dto.SaleNotificationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebSocketService {

    private final SimpMessagingTemplate messagingTemplate;

    public void notifySaleStart(SaleNotificationDto sale) {
        messagingTemplate.convertAndSend("/topic/sales", sale);
    }

    public void notifySaleStop(SaleNotificationDto sale) {
        messagingTemplate.convertAndSend("/topic/sales", sale);
    }
}