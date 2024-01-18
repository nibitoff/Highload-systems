package com.alsab.boozycalc.party.websocket;

import com.alsab.boozycalc.party.dto.CocktailTypeDto;
import com.alsab.boozycalc.party.dto.PartyDto;
import com.alsab.boozycalc.party.dto.SaleDto;
import com.alsab.boozycalc.party.dto.SaleNotificationDto;
import com.alsab.boozycalc.party.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.converter.CompositeMessageConverter;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class WebSocketNotificationClient implements StompSessionHandler {

    private static final String wsServerUrl = "ws://notificationapp:7777/our-websocket";
    private static final String subscribeTopic = "/topic/sales";
    private StompSession stompSession;

    private final SaleService saleService;

    @EventListener(value = ApplicationReadyEvent.class)
    public void connect() {
        WebSocketClient client = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        List<MessageConverter> converters = new ArrayList<MessageConverter>();
        converters.add(new MappingJackson2MessageConverter()); // used to handle json messages
        converters.add(new StringMessageConverter()); // used to handle raw strings
        stompClient.setMessageConverter(new CompositeMessageConverter(converters));
        try {
            stompSession = stompClient.connectAsync(wsServerUrl, this).get();
            stompSession.subscribe(subscribeTopic, this);
        } catch (Exception e) {
            stompSession.subscribe(subscribeTopic, this);
            e.printStackTrace();
        }
    }


    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        System.out.println(exception.getMessage());
    }

    @Override
    public void handleTransportError(StompSession session, Throwable exception) {
        if (!session.isConnected()) {
            connect();
        }

    }

    @Override
    public Type getPayloadType( StompHeaders headers) {
        return SaleNotificationDto.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        SaleNotificationDto saleNotificationDto = (SaleNotificationDto) payload;
        System.out.println("RECEIVED: " + saleNotificationDto.toString());
        if (saleNotificationDto.getSalePercent() <= 0) {
            saleService.deleteByPartyAndType(
                    PartyDto.builder().id(saleNotificationDto.getPartyId()).build(),
                    CocktailTypeDto.builder().id(saleNotificationDto.getCocktailTypeId()).build()
            );
        } else {
            saleService.add(new SaleDto(saleNotificationDto));
        }
    }

}
