package com.alsab.boozycalc.notification.controller;

import com.alsab.boozycalc.notification.dto.CocktailDoneDto;
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

    @PostMapping("/start-sale")
    public void startSale(@RequestBody SaleNotificationDto sale) {
        service.notifySaleStart(sale);
    }

    @PostMapping("/cocktail-done")
    public void cocktailDone(@RequestBody CocktailDoneDto done) {
        service.notifyCocktailDone(done);
    }
}
