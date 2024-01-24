package com.alsab.boozycalc.notification.controller;

import com.alsab.boozycalc.notification.dto.CocktailDoneDto;
import com.alsab.boozycalc.notification.dto.SaleNotificationDto;
import com.alsab.boozycalc.notification.service.WebSocketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Notification controller", description = "Controller to manage notification")
@RestController
@RequiredArgsConstructor
public class WebSocketController {

    private final WebSocketService service;

    @PostMapping("/start-sale")
    @Operation(description = "Start sale notifications",
            summary = "Start sale notifications",
            tags = "notifications")
    public void startSale(@RequestBody SaleNotificationDto sale) {
        service.notifySaleStart(sale);
    }

    @PostMapping("/cocktail-done")
    @Operation(description = "Notificate for users about cocktail",
            summary = "Notificate for users about cocktail",
            tags = "notifications")
    public void cocktailDone(@RequestBody CocktailDoneDto done) {
        service.notifyCocktailDone(done);
    }
}
